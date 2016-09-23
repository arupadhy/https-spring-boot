package com.sapient.app.shop.org.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sapient.app.shop.org.model.Attribute;
import com.sapient.app.shop.org.model.Media;
import com.sapient.app.shop.org.model.ProductProfile;
import com.sapient.app.shop.org.model.Scores;
import com.sapient.app.shop.org.repository.ProductProfileRepository;

@Component
public class DataLoader {

	private Map<Integer, String> attributesInXl = new HashMap<>();
	private Map<String, Attribute> labelToAttrMap = new HashMap<>();
	private static Logger LOGGER = Logger.getLogger(DataLoader.class);

	@Autowired
	private ProductProfileRepository productProfilesRepo;

	@PostConstruct
	private void init() {
		attributesInXl.put(8, "creative");
		attributesInXl.put(9, "diy");
		attributesInXl.put(10, "fitness");
		attributesInXl.put(11, "home");
		attributesInXl.put(12, "health");
		attributesInXl.put(13, "intellectual");
		attributesInXl.put(14, "musical");
		attributesInXl.put(15, "outdoor");
		attributesInXl.put(16, "professional");
		attributesInXl.put(17, "social");
		attributesInXl.put(18, "tech");
		attributesInXl.put(19, "travel");
		attributesInXl.put(20, "food");

	}



	private List<Media> generateMediaType(String url){
		List<Media> media = new ArrayList<>();
		Media m  = new Media();
		m.setContentType("image");
		m.setUrl(url);
		media.add(m);
		return media;
	}

	private Map<String,Scores> generateFormattedAttribute(List<Map<String, Object>> attributesWithScores) {
		Map<String, Scores> data = new HashMap<>();

		for(Map<String, Object> map: attributesWithScores) {
			Scores scores =new Scores();
			Attribute attributeFromXl = (Attribute) map.get("attr");
			scores.setConfidence(0.1f);
			String we = (String) map.get("weight");
			if(StringUtils.isEmpty(we)) {
				System.out.println("Empty weight");
				we = "0.0";
			}
			if(we.contains(".+") || we.contains(".-")){
				we = "0.0";
			}
			we = we.trim();
			float weight = 0.0f;
			try{
				weight = Float.parseFloat(we);
			} catch(NumberFormatException e) {
				LOGGER.error("Could not convert the weight for " + map.keySet());
			}
			LOGGER.info("weight: " +weight);
			scores.setWeight(weight);
			data.put(attributeFromXl.getName(), scores);
		}
		return data;
	}

	public void loadXl() {
		Set<String> productSet = new HashSet<>();
		//List<ProductProfile> productProfiles = new ArrayList<>();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("AITeamContentMatrixV16_ContentTeamFinal.xlsx");
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Sheet attrSheet = workbook.getSheetAt(0);
		Iterator<Row> attrItr = attrSheet.iterator();
		attrItr.next();
		while(attrItr.hasNext()) {
			Row row = attrItr.next();
			Attribute attr = new Attribute();
			if(row.getCell(0) == null) {
				break;
			}
			attr.setLabel(row.getCell(0).getStringCellValue().toLowerCase());
			attr.setName(row.getCell(1).getStringCellValue().toLowerCase());
			attr.setSynonyms(row.getCell(3).getStringCellValue());
			labelToAttrMap.put(attr.getLabel().toLowerCase(), attr);
		}


		Sheet firstSheet = workbook.getSheetAt(1);
		Iterator<Row> iterator = firstSheet.iterator();
		iterator.next(); //skip empty rows
		iterator.next();
		while (iterator.hasNext()) {
			List<Map<String, Object>> attributesWithScores = new ArrayList<>();
			ProductProfile productProfile = new ProductProfile();
			Row nextRow = iterator.next();
			attributesInXl.entrySet().forEach(entry -> {
				Map<String, Object> map = new HashMap<>();
				if(labelToAttrMap.get(entry.getValue()) == null) {
					return;
				}
				int cellType =6;
				if(nextRow.getCell(entry.getKey()) != null) {
					cellType = nextRow.getCell(entry.getKey()).getCellType();
				}
				String value = "";
				if(cellType == Cell.CELL_TYPE_NUMERIC) {
					value = Double.toString(nextRow.getCell(entry.getKey()).getNumericCellValue());
				}else if(cellType == Cell.CELL_TYPE_STRING) {
					value = nextRow.getCell(entry.getKey()).getStringCellValue();
				}
				map.put("weight", value);
				map.put("attr", labelToAttrMap.get(entry.getValue().toLowerCase()));
				attributesWithScores.add(map);
			});

			if(nextRow.getCell(0) != null && !StringUtils.isEmpty(nextRow.getCell(0).getStringCellValue())) {
				productProfile.setName(nextRow.getCell(0).getStringCellValue());
				productProfile.setFrontEndProductId(nextRow.getCell(1).getStringCellValue());
				productProfile.setMedia(generateMediaType(nextRow.getCell(2).getStringCellValue()));
				productProfile.setDescription(nextRow.getCell(4).getStringCellValue());
				//bad data in the final xl sheet
				productProfile.setShortDescription(nextRow.getCell(6).getCellType() == Cell.CELL_TYPE_STRING ?nextRow.getCell(5).getStringCellValue():"");
				productProfile.setAttributes(generateFormattedAttribute(attributesWithScores));
				//productProfiles.add(productProfile);
				if(!StringUtils.isEmpty(productProfile.getDescription())) {
					ProductProfile p = productProfilesRepo.save(productProfile);
					productSet.add(p.getFrontEndProductId());
					System.out.println(productProfile.getAttributes().values());
					
					LOGGER.info(String.format("Added product profile %s with id %s to product collection", p.toString(),p.getId()));
				}

			}


		}
		LOGGER.info(String.format("Count of products loaded via DataLoader is %s", productSet.size()));
	}

}
