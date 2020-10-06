package com.diusframi.tpv.crearpdf;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class ParseDOM {
    Context context;
    Document document = null;

    public ParseDOM(Context context)
    {
        this.context = context;
    }


    /*Método que devolverá la referencia al documento XML cargado en memoria.*/
    public Document getDocument(InputStream inputStream)
    {

        /*Se asigna a una variable de tipo DocumentBuilderFactory, una nueva instancia
        para definir una factoría, que permita a la aplicación producir un árbol de objetos DOM
        a partir del documento XML procesado.*/
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try
        {
            /*Se obtiene la instancia del documento DOM del fichero XML.*/
            DocumentBuilder db = factory.newDocumentBuilder();
            /*Se instancia la clase InputSource, para definir un recurso de entrada
            para la entidad XML.*/
            InputSource inputSource = new InputSource(inputStream);
            /*Por último a la instancia Document se le asigna el contenido
            del recurso de entrada del documento XML, devolviendo un nuevo objeto Document.*/
            document = db.parse(inputSource);
        } catch (ParserConfigurationException e)
        {
            Log.e("Error", e.getMessage());
            return null;
        } catch (SAXException e)
        {
            Log.e("Error", e.getMessage());
            return null;
        } catch (IOException e)
        {
            Log.e("Error", e.getMessage());
            return null;
        }
        return document;
    }

    /*Método que mostrará el texto de cada nodo hijo (nodos finales).*/
    public String getValue(Element item, String name)
    {
        NodeList nodes = item.getElementsByTagName(name);
        return this.getTextNodeValue(nodes.item(0));
    }

    /*Método que devolverá un String con el texto de cada nodo hijo.*/
    private final String getTextNodeValue(Node node)
    {
        Node child;
        /*Se comprueba que no sea nulo la referencia Node recibida.*/
        if (node != null)
        {
            /*A continuación, se comprueba si el nodo actual tiene nodos hijos.*/
            if (node.hasChildNodes())
            {
                /*Se posiciona en el primer nodo hijo.*/
                child = node.getFirstChild();
                /*Se define un bucle while() que comprueba en cada iteración si existe un próximo nodo hijo.*/
                while(child != null)
                {
                    if (child.getNodeType() == Node.TEXT_NODE)
                    {
                        return child.getNodeValue();
                    }
                    child = child.getNextSibling();
                }
            }
        }
        return "";
    }
}
