package generator.mbgcomment;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.GeneratedKey;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyInsertElementGenerator extends AbstractXmlElementGenerator {
    private boolean isSimple;

    public MyInsertElementGenerator(boolean isSimple) {
        this.isSimple = isSimple;
    }

    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");
        char[] domainObjectName=this.introspectedTable.getTableConfiguration().getDomainObjectName().toCharArray();
        domainObjectName[0]+=32;
        answer.addAttribute(new Attribute("id", this.introspectedTable.getInsertStatementId()));
        FullyQualifiedJavaType parameterType;
     /*   if (this.isSimple) {
            parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        } else {
            parameterType = this.introspectedTable.getRules().calculateAllFieldsClass();
        }*/

        answer.addAttribute(new Attribute("parameterType", String.valueOf(domainObjectName)));
        this.context.getCommentGenerator().addComment(answer);
        GeneratedKey gk = this.introspectedTable.getGeneratedKey();
        if (gk != null) {
            IntrospectedColumn introspectedColumn = this.introspectedTable.getColumn(gk.getColumn());
            if (introspectedColumn != null) {
                if (gk.isJdbcStandard()) {
                    answer.addAttribute(new Attribute("useGeneratedKeys", "true"));
                    answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));
                    answer.addAttribute(new Attribute("keyColumn", introspectedColumn.getActualColumnName()));
                } else {
                    answer.addElement(this.getSelectKey(introspectedColumn, gk));
                }
            }
        }

        StringBuilder insertClause = new StringBuilder();
        insertClause.append("insert into ");
        insertClause.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" (");
        StringBuilder valuesClause = new StringBuilder();
        valuesClause.append("values (");
        List<String> valuesClauses = new ArrayList();
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(this.introspectedTable.getAllColumns());

        for(int i = 0; i < columns.size(); ++i) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)columns.get(i);

            insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
           // valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            valuesClause.append("#{");
            valuesClause.append(introspectedColumn.getJavaProperty(null));
            valuesClause.append('}');
            if (i + 1 < columns.size()) {
                insertClause.append(", ");
                valuesClause.append(", ");
            }

            if (valuesClause.length() > 80) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);
                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
        }

        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));
        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());
        Iterator var12 = valuesClauses.iterator();

        while(var12.hasNext()) {
            String clause = (String)var12.next();
            answer.addElement(new TextElement(clause));
        }

        if (this.context.getPlugins().sqlMapInsertElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }

    }
}
