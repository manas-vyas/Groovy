from atlassian import Confluence
import osfrom bs4 
import BeautifulSoup
import pandas as pd
from IPython.display 
import HTML
import sys
service = sys.argv[1]
tag = sys.argv[2]
datetime = sys.argv[3]
url = sys.argv[4]
region = sys.argv[5]
user = "hhjjjkjfkjfkjfkffk"
api_key = "kfjjfjfkfjkfjkf"
server = "https://jkjfkjfkjjkrnd.atlassian.net"
confluence = Confluence(url=server, username=user, password=api_key, verify_ssl=False)
#page = confluence.get_page_by_title("MLOP", "Test_automation", expand="body.storage")
#body = page["body"]["storage"]["value"]
body=confluence.get_page_by_id('7683677777',expand='body.storage').get('body').get('storage').get('value')
#print(body)
tables_raw = [[[cell.text for cell in row("th") + row("td")]                    
                    for row in table("tr")]                    
                    for table in BeautifulSoup(body, features="lxml")("table")]
tables_df = [pd.DataFrame(table) for table in tables_raw]
#print(tables_df[1])
#exit()
updated_body = ""
for table_df in tables_df:    
    value = table_df[0].values[0]    
    #print(value)    
    if value == 'QA Services':        
        df = pd.DataFrame(table_df)        
        #print(df)        
        ind = df.index[df[0]==service].tolist()        
        #print(df.loc[ind])
        if region == "us-east-1":
            df.loc[ind, [1]] = [tag]        
            df.loc[ind, [2]] = [datetime]   
            df.loc[ind, [3]] = [url] 
        else:            
            df.loc[ind, [4]] = [tag]        
            df.loc[ind, [5]] = [datetime]   
            df.loc[ind, [6]] = [url] 
        #print(df.loc[ind])
   # table_df.columns = table_df.iloc[0]
    #table_df = table_df[1:]
    print(table_df)
    html_table = table_df.to_html(index=False, header=False)
    updated_body += html_table



updated_body = updated_body.replace("<th></th>", "")
print(updated_body)
#exit()
status = confluence.update_page(
    page_id='3183640868',
    title='Test Page',
    body=updated_body,
    parent_id=None,
    type='page',
    representation='storage',
    minor_edit=False,
    full_width=False
)

print(status)

