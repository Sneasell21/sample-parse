package com.company;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.google.gson.internal.LinkedTreeMap;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Main {



    public static void main(String[] args) {


        JsonElement root = null;
        Config config =null;
        //
        try {
            root = new JsonParser().parse(new FileReader("/home/mdiaz/Downloads/miscelanius/jsonParser/src/Resources/sample2.json"));
          //  config = ConfigFactory.parseFile(new File("/home/mdiaz/Downloads/miscelanius/jsonParser/src/Resources/hoconExample.conf"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        @SuppressWarnings("unchecked")
        LinkedTreeMap<String,LinkedTreeMap>  map = new Gson().fromJson(root.toString(),LinkedTreeMap.class);
        Object node = map.get(map.keySet().stream().findFirst().get());
        //Connect.insert(0,null,map.keySet().stream().findFirst().get(),"");
        System.out.println("root node hascode "+map.hashCode());
        navigateIntoSet("",(LinkedTreeMap)node,0,map.hashCode());
    }

    public static void navigateIntoSet(String elem, LinkedTreeMap map,int parent,int id) {

        Object node = null;
        if (elem.isEmpty()) {
            node = map;
        } else {
            node = map.get(elem);
        }

        if (node == null) return;

        if (node instanceof Collection<?>){
            ArrayList<LinkedTreeMap> collectionNode = (ArrayList)node;
            System.out.println("parent::"+id+" ::id:"+(collectionNode.hashCode())+" ArrayList ");
            for (LinkedTreeMap innerElem : collectionNode) {
                    navigateIntoSet("", innerElem,id,collectionNode.hashCode());
            }
            return;
        }
        if (node instanceof LinkedTreeMap) {
            LinkedTreeMap nodeCast = (LinkedTreeMap)node;
            for (Object innerElem : nodeCast.keySet()) {
                System.out.println("parent::"+id+" ::id:"+(nodeCast.hashCode())+" "+innerElem);
                navigateIntoSet(innerElem.toString(), nodeCast,id,nodeCast.hashCode());
            }
            return;
        }

            System.out.println("nil::" + node.toString());
        return;

    }
}
