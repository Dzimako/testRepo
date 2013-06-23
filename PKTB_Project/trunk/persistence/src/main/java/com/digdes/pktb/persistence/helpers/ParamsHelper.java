package com.digdes.pktb.persistence.helpers;

import com.digdes.pktb.persistence.beans.wsbeans.requests.Pair;

import javax.xml.crypto.Data;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParamsHelper {
    private static Map<String, Map<String, String>> keyMap;

    static {
        StringBuilder nowDDMMYYYY = new StringBuilder( (new SimpleDateFormat("dd.MM.yyyy")).format(new Date()) );
        Map<String,  Map<String, String>> aMap = new HashMap<String, Map<String, String>>();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("depoP","0");
//        paramsMap.put("dateOtch","2012-09-21");
        paramsMap.put("dateOtch",new String(nowDDMMYYYY));
        paramsMap.put("vidT","*");
        paramsMap.put("serLoc","*");
        paramsMap.put("rodS","*");
        paramsMap.put("rodT","*");
        paramsMap.put("shirinaKolei","0");
        paramsMap.put("edVidachi","1");
        aMap.put("spl3072_inv", paramsMap);
        paramsMap = new HashMap<String, String>();
        paramsMap.put("depoP","0");
        paramsMap.put("dateOtch",new String(nowDDMMYYYY));
        paramsMap.put("vidT","*");
        paramsMap.put("serLoc","*");
        paramsMap.put("rodS","*");
        paramsMap.put("rodT","*");
        paramsMap.put("shirinaKolei","0");
        paramsMap.put("edVidachi","1");
        aMap.put("spl3072_neexp", paramsMap);
        paramsMap = new HashMap<String, String>();
        paramsMap.put("N0","2012");
        paramsMap.put("N1","09");
        paramsMap.put("N2","21");
        paramsMap.put("N3","13");
        paramsMap.put("N4","30");
        paramsMap.put("N10","001");
        paramsMap.put("N11","1");
        paramsMap.put("N12","1");
        aMap.put("spl3204", paramsMap);
        paramsMap = new HashMap<String, String>();
        paramsMap.put("N0",new SimpleDateFormat("yyyy").format(new Date()));
        paramsMap.put("N1",new SimpleDateFormat("MM").format(new Date()));
        paramsMap.put("N2",new SimpleDateFormat("dd").format(new Date()));
        paramsMap.put("N3","13");
        paramsMap.put("N4","30");
        paramsMap.put("N10","001");
        paramsMap.put("N11","1");
        paramsMap.put("N12","1");
        aMap.put("spl3202", paramsMap);
        paramsMap = new HashMap<String, String>();
        paramsMap.put("N0",new SimpleDateFormat("yyyy").format(new Date()));
        paramsMap.put("N1",new SimpleDateFormat("MM").format(new Date()));
        paramsMap.put("N2",new SimpleDateFormat("dd").format(new Date()));
        paramsMap.put("N3","13");
        paramsMap.put("N4","30");
        paramsMap.put("N10","001");
        paramsMap.put("N11","1");
        paramsMap.put("N12","1");
        aMap.put("spl3203", paramsMap);
        paramsMap = new HashMap<String, String>();
        paramsMap.put("period","2012");
        paramsMap.put("dorPr","*");
        paramsMap.put("depoPr","*");
        paramsMap.put("W4_and_W5","*");
        paramsMap.put("concrete_station","0");
        paramsMap.put("concrete_depo","0");
        paramsMap.put("vidT","*");
        paramsMap.put("W7","*");
        aMap.put("spl3068", paramsMap);
        paramsMap = new HashMap<String, String>();
        paramsMap.put("P4","28");
        paramsMap.put("P0","*");
        paramsMap.put("P2","*");
        paramsMap.put("P3","*");
        aMap.put("spl3157", paramsMap);
        paramsMap = new HashMap<String, String>();
        paramsMap.put("N0",new SimpleDateFormat("yyyy").format(new Date()));
        paramsMap.put("N1",new SimpleDateFormat("MM").format(new Date()));
        paramsMap.put("N2",new SimpleDateFormat("dd").format(new Date()));
        paramsMap.put("N3","13");
        paramsMap.put("N4","30");
        paramsMap.put("N0K",new SimpleDateFormat("yyyy").format(new Date()));
        paramsMap.put("N1K",new SimpleDateFormat("MM").format(new Date()));
        paramsMap.put("N2K",new SimpleDateFormat("dd").format(new Date()));
        paramsMap.put("N3K","13");
        paramsMap.put("P8","*");
        paramsMap.put("N4K","30");
        paramsMap.put("P10","*");
        aMap.put("spl3132", paramsMap);
        paramsMap = new HashMap<String, String>();
        paramsMap.put("depoP","0");
        paramsMap.put("dateOtch",new String(nowDDMMYYYY));
        paramsMap.put("vidT","*");
        paramsMap.put("serLoc","*");
        paramsMap.put("rodS","*");
        paramsMap.put("rodT","*");
        paramsMap.put("shirinaKolei","0");
        paramsMap.put("edVidachi","2");
        aMap.put("spl3072_exp", paramsMap);
        keyMap = Collections.unmodifiableMap(aMap);
    }


    //Получает значение настроек по ключу
    public static Set<Pair> getParamsMapByKey(String key) {
//        System.out.println("key: "+key);
        Set<Pair> pairs = new HashSet<Pair>();
        for (String keyIter: keyMap.get(key).keySet()){
            Pair pair = new Pair();
            pair.setName(keyIter);
            pair.setValue(keyMap.get(key).get(keyIter));
            pairs.add(pair);
        }
        return pairs;
    }



}