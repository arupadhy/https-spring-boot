package com.sapient.app.shop.org.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.sapient.app.shop.org.model.Question;
import com.sapient.app.shop.org.model.QuestionTree;

@Repository
public class QuestionLoader {
	
	private List<String> attributes = new ArrayList<>();
	private static Logger LOGGER = Logger.getLogger(QuestionLoader.class);

	@PostConstruct
	private void init() {
		attributes.add("creative");
		attributes.add("diy");
		attributes.add("fitness");
		attributes.add("home");
		attributes.add("health");
		attributes.add("intellectual");
		attributes.add("musical");
		attributes.add("outdoor");
		attributes.add("professional");
		attributes.add("social");
		attributes.add("tech");
		attributes.add("travel");
		attributes.add("food");
	}

	public List<Question>  loadAllQuestions() {
		List<Question> questionsList = new ArrayList<>();
		Map<String, Question> questionMap = new HashMap<>();
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(Thread.currentThread().getContextClassLoader().getResourceAsStream("QuestionListNew.xlsx"));
		} catch (IOException e) {
			LOGGER.error("Could not load questions......other services should work fine...");
			LOGGER.error(e);
		}

		Sheet attrSheet = workbook.getSheet("Final");
		Iterator<Row> attrItr = attrSheet.iterator();
		attrItr.next();
		while(attrItr.hasNext()) {
			Question question = new Question();
			Row row = attrItr.next();

			String cat = row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
			question.setCategory(cat);

			String text = row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue().replaceAll("\n", "");
			question.setText(text);
			question.setXlId(row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			question.setAudio(question.getXlId() + ".wav");

			String aiResponse = row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue().replaceAll("\n", "");
			if(!StringUtils.isEmpty(aiResponse) && !aiResponse.contains("=")) {
				question.setQuipText(aiResponse);
				question.setQuipAudio(question.getXlId() + "-quip.wav");
			}

			Map<String, Map<String,Double>> attr = new HashMap<>();
			int cellNumber = 4;
			for (int i = 0; i < attributes.size(); i++) {
				Map<String, Double> map = new HashMap<>();
				map.put("weight", row.getCell(cellNumber + i, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
				attr.put(attributes.get(i), map);
			}

			question.setAttributes(attr);
			questionsList.add(question);
			questionMap.put(question.getXlId(), question);
		}

		Sheet treeSheet = workbook.getSheet("Final Trees");
		Iterator<Row> treeItr = treeSheet.iterator();
		treeItr.next();
		while(treeItr.hasNext()) {
			Row row = treeItr.next();
			String qid = row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
			Map<String,QuestionTree> questionTreeMap = questionMap.get(qid).getQuestionTree();
			QuestionTree questionTree = new QuestionTree();
			questionTree.setAnswerKey(row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

			Cell c = row.getCell(2, Row.CREATE_NULL_AS_BLANK);
			c.setCellType(1);
			questionTree.setAnswerText(c.getStringCellValue());

			questionTree.setFollowUpId(row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			questionTree.setFollowUpAudio(questionTree.getFollowUpId() + ".wav");
			Map<String, Map<String, Double>> scores = new HashMap<>();
			int cellNumber = 4;
			for (int i = 0; i < attributes.size(); i++) {
				Map<String, Double> map = new HashMap<>();
				c = row.getCell(cellNumber + i, Row.CREATE_NULL_AS_BLANK);
				c.setCellType(0);
				map.put("weight", c.getNumericCellValue());
				scores.put(attributes.get(i), map);
			}
			questionTree.setAttributeScores(scores);
			questionTreeMap.put(questionTree.getAnswerText(), questionTree);
		}

		LOGGER.info(String.format("questions loaded in memory {}",questionsList.size()));
		return questionsList;
	}

	
}