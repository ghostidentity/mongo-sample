package me.mtagab.service;

import org.apache.commons.lang3.StringUtils;
import org.fastily.jwiki.core.Wiki;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WikiService {

    Wiki wiki = new Wiki.Builder().build();

    public WikiService() {
        String username = "proxyanalyst25";
        String password = "M?CK-pPM894LRi?";
        wiki.login(username, password);
    }

    public void connect() {
        String username = "proxyanalyst25";
        String password = "M?CK-pPM894LRi?";
        wiki.login(username, password);
    }

    public String getPageResult() {
        return wiki.getPageText("List_of_scientists_whose_names_are_used_in_physical_constants");
    }

    public List<String> extract(String input) {
        String[] value_split = input.split("\\|-");
        ArrayList<String> result = new ArrayList<String> ();
        for (String raw: value_split) {
            String lines[] = raw.split("\\r?\\n");
            if(lines.length == 5) {
                result.add(StringUtils.substringBetween(lines[1], "|[[", "]]"));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        WikiService wiki = new WikiService();
        wiki.connect();

       String raw =  wiki.getPageResult();
       List<String> result = wiki.extract(raw);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }
}
