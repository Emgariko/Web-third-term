import os

from progress.bar import IncrementalBar

def get_str(x): 

    s = """curl 'http://1d3p.wp.codeforces.com/new' \
    -H 'Connection: keep-alive' \
    -H 'Cache-Control: max-age=0' \
    -H 'Upgrade-Insecure-Requests: 1' \
    -H 'Origin: http://1d3p.wp.codeforces.com' \
    -H 'Content-Type: application/x-www-form-urlencoded' \
    -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36' \
    -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9' \
    -H 'Referer: http://1d3p.wp.codeforces.com/' \
    -H 'Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7' \
    -H 'Cookie: __utmc=71512449; evercookie_etag=bhe6pr86rxwnf3edn2; evercookie_cache=bhe6pr86rxwnf3edn2; evercookie_png=bhe6pr86rxwnf3edn2; 70a7c28f3de=bhe6pr86rxwnf3edn2; __utma=71512449.615939875.1593532841.1599928415.1600202797.13; __utmz=71512449.1600202797.13.7.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); JSESSIONID=5031A53AED925A75F7A10591C3431C93' \
    --data-raw '_af=34be50b38beccce4&proof=""" + str(x * x) + """&amount=""" + str(x) + """&submit=Submit' \
    --compressed \
    --insecure"""
    return s

bar = IncrementalBar('Countdown', max = 100)

for i in range(1, 101):
    os.system(get_str(i))
    bar.next()
    
bar.finish()
