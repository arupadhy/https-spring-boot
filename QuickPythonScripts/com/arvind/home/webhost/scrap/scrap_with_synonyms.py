import urllib.request
import json
from openpyxl import Workbook
from urllib.parse import quote

# Creates an xl sheet with text, title, attribute and the source using webhose api
class AttributeScrapper(object):

    def __init__(self, xl_file_name):
        self.attributes_with_synonyms = {'Creative': 'artistic, creating, original, art',
                                         'DIY': 'do-it-yourself, handy, home, improvement, handmade, self, repairs, '
                                                'pinterest',
                                         'Fitness': 'exercise, health, workout, strength',
                                         'Home': 'house, household, family',
                                         'Health': 'energy, strength, well-being, fitness, good, diet',
                                         'Intellectual': 'smart, cerebral, bookish, scholarly, thoughtful, rational, '
                                                         'brainy, intelligent, learned, studious',
                                         'Musical': 'rhythmic, melodic, vocal, choral, symphonic, musician, '
                                                    'audiophile, audio',
                                         'Outdoor': 'outside, patio, picnic, beach, '
                                                    'mountains, parks, garden',
                                         'Professional': 'experienced, skilled, trained, licensed, qualified '
                                                         'professions, executive, white-collar, authority, educated',
                                         'Social': 'communal, sociable, partier,  community, friendly, '
                                                   'companionable, gregarious',
                                         'Tech': 'techie, computer specialist, hacker, engineer, technophile, geek, '
                                                 'nerd, gear, toys, games',
                                         'Travel': 'trek, tour, visit, sightsee, sightseeing, excursion, '
                                                   ' voyage',
                                         'Food': 'restaurant, foodie, food, eat, cook, prepare, chef, wine'
                                         }

        self.xl_file_name = xl_file_name
        self.BASE_URL = "https://webhose.io"
        self.workbook = Workbook()
        self.sheet = self.workbook.active
        self.setup_header_xl()

    def setup_header_xl(self):
        self.sheet['A1'] = "Title"
        self.sheet['B1'] = "Text"
        self.sheet['C1'] = "Attribute"
        self.sheet['D1'] = "Url Of the Source"

    def write_to_xl(self, content, word):
        result = json.loads(content.decode('utf8'))
        for post in result['posts']:
            if post['title'] and  post['text']:
                data = []
                data.append(post['title'])
                data.append(post['text'])
                data.append(word)
                data.append(post['url'])
                print(data)
                print('\n\n')
                self.sheet.append(data)
                self.workbook.save(self.xl_file_name)

        return {"next_page": result['next'], "count": result['totalResults'], "more": result['moreResultsAvailable']}

    def scrap_english_blogs(self):
        # for each word in the dataSet
        for word in self.attributes_with_synonyms:
            count_rows = 0
            syn_query = []
            synonyms = self.attributes_with_synonyms[word]
            # get all synonyms and build the query
            syn_query.append(word)
            for syn in synonyms.split(','):
                syn_query.append(" OR ")
                syn_query.append(syn)

            # build url with synonyms and performance score better than 4
            syn_url_query = "".join(syn_query)
            url = self.BASE_URL + "/search?token=8a793f92-3177-433b-a40a-760e9c53fcd6&format=json&q=" + syn_url_query  + \
                  "%20language%3A(english)%20performance_score%3A%3E9%20(site_type%3Ablogs)"
            print("Getting Results from " + url)
            no_more_result = False
            while count_rows < 200 and not no_more_result:
                web_host_api = urllib.request.urlopen(url)
                web_host_res = web_host_api.read()
                print(web_host_res)
                res = self.write_to_xl(web_host_res, word)
                count_rows = count_rows + res['count']
                url = self.BASE_URL + res['next_page']
                if res['more'] == 0:
                    no_more_result = True

# start the scrapping process

scrapper = AttributeScrapper("attributes_data_scrapped.xlsx")
print("Started Scrapping web for all attributes: " + str(scrapper.attributes_with_synonyms.keys()))
scrapper.scrap_english_blogs()
print('Completed Scrapping and created '+scrapper.xl_file_name)