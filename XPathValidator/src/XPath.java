import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;


public class XPath {
	public static void main(String[] args) {		
		System.out.println("Execution Start");
		StringBuffer requestTemplate = new StringBuffer();
		String tmp = null;
		requestTemplate = new StringBuffer("");
		BufferedReader br = null;
		try {
			br  = new BufferedReader(new FileReader("D:\\response.xml"));
			while((tmp = br.readLine())!=null){
				requestTemplate.append(tmp);
			}	
		}catch(Throwable e){
			e.printStackTrace();
		}finally{
			if(br!=null) {
				try {
					br.close();
				} catch (IOException e) {
				}
				br=null;
			}
		}

		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(requestTemplate.toString().getBytes());
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentbuilder = factory.newDocumentBuilder();
			Document responseDoc = documentbuilder.parse(bis);

			javax.xml.xpath.XPath xpath  = XPathFactory.newInstance().newXPath();
			String s = ((javax.xml.xpath.XPath) xpath).compile("/*[local-name()='umb']/*[local-name()='event']/@statuscode").evaluate(responseDoc);
            System.out.println("XPATH VALUE: "+s);
				}catch(Exception e){
             System.out.println("ERROR:"+e);       
            }

			}
}