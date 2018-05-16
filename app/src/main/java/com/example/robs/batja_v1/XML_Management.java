package com.example.robs.batja_v1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Robs on 16.05.18.
 */

public class XML_Management {

    private XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
    private XmlPullParser myparser = xmlFactoryObject.newPullParser();

    public XML_Management() throws XmlPullParserException {
    }




    public void wirteXML() {


        /*try {
            Document doc = null;
            Element element;
            NodeList nList;
            try (InputStream is = getContext.getAssets().open( "file.xml" )) {

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse( is );
            } catch (ParserConfigurationException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (SAXException e1) {
                e1.printStackTrace();
            }

            element = doc.getDocumentElement();
            element.normalize();

            nList = doc.getElementsByTagName( "location" );

            for (int i = 0; i < nList.getLength(); i++) {

                Node node = nList.item( i );
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;
                    //tv1.setText(tv1.getText()+"\nName : " + getValue("name", element2)+"\n");
                    //tv1.setText(tv1.getText()+"Surname : " + getValue("surname", element2)+"\n");
                    //tv1.setText(tv1.getText()+"-----------------------");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

*/
    }
}
