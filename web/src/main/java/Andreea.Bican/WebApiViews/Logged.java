package Andreea.Bican.WebApiViews;

import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Response;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.Base64;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by andre on 25.08.2016.
 */
@RestController
public class Logged {

    @RequestMapping(value="/logged", method = RequestMethod.POST )
    public @ResponseBody
    String createMessage(HttpServletRequest httpServletRequest ) throws ParserConfigurationException, IOException, SAXException, UnmarshallingException {
        String responseMessage = httpServletRequest.getParameter("SAMLResponse");
        System.out.println(responseMessage);
        byte[] base64DecodedResponse = Base64.decode(responseMessage);

        ByteArrayInputStream is = new ByteArrayInputStream(base64DecodedResponse);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = docBuilder.parse(is);
        Element element = document.getDocumentElement();

        UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
        Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(element);
        XMLObject responseXmlObj = unmarshaller.unmarshall(element);

        Response response = (Response) responseXmlObj;

        Assertion assertion = response.getAssertions().get(0);

        String subject = assertion.getSubject().getNameID().getValue();
        String issuer = assertion.getIssuer().getValue();
        String audience = assertion.getConditions().getAudienceRestrictions().get(0).getAudiences().get(0).getAudienceURI();
        String statusCode = response.getStatus().getStatusCode().getValue();

        String message = subject + " " + issuer + " " + audience + " " + statusCode;
        return message;
    }
}
