package generator.mbgcomment;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.Iterator;

public class MyUpdateByPrimaryKeySelectiveElementGenerator extends AbstractXmlElementGenerator {
    public MyUpdateByPrimaryKeySelectiveElementGenerator() {
    }

    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update");
        char[] domainObjectName=this.introspectedTable.getTableConfiguration().getDomainObjectName().toCharArray();
        domainObjectName[0]+=32;
        answer.addAttribute(new Attribute("id", this.introspectedTable.getUpdateByPrimaryKeySelectiveStatementId()));
        String parameterType;

      /*  if (this.introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            parameterType = this.introspectedTable.getRecordWithBLOBsType();
        } else {
            parameterType = this.introspectedTable.getBaseRecordType();
        }*/
        parameterType=String.valueOf(domainObjectName);
        answer.addAttribute(new Attribute("parameterType", parameterType));
        this.context.getCommentGenerator().addComment(answer);
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        XmlElement dynamicElement = new XmlElement("set");
        answer.addElement(dynamicElement);
        Iterator var6 = ListUtilities.removeGeneratedAlwaysColumns(this.introspectedTable.getNonPrimaryKeyColumns()).iterator();

        while(var6.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)var6.next();
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null");
            XmlElement isNotNullElement = new XmlElement("if");
            isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            dynamicElement.addElement(isNotNullElement);
            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append("#{");
            sb.append(introspectedColumn.getJavaProperty(null));
            sb.append('}');
            //sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            if (var6.hasNext()) {
                sb.append(',');
            }
            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

        boolean and = false;
        Iterator var10 = this.introspectedTable.getPrimaryKeyColumns().iterator();

        while(var10.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)var10.next();
            sb.setLength(0);
            if (and) {
                sb.append("  and ");
            } else {
                sb.append("where ");
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append("#{");
            sb.append(introspectedColumn.getJavaProperty(null));
            sb.append('}');
            //sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }

        if (this.context.getPlugins().sqlMapUpdateByPrimaryKeySelectiveElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }

    }
}
