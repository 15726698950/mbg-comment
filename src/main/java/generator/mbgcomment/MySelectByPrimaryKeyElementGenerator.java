package generator.mbgcomment;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Iterator;
import java.util.List;

public class MySelectByPrimaryKeyElementGenerator extends AbstractXmlElementGenerator {
    public MySelectByPrimaryKeyElementGenerator() {
    }


    /**
     * 生成sql 的代码
     * @param parentElement
     */
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        //因为我的项目中使用 别名所以paramType 和result 改成自己 想要的方式
        char[] domainObjectName=this.introspectedTable.getTableConfiguration().getDomainObjectName().toCharArray();
        domainObjectName[0]+=32;

        answer.addAttribute(new Attribute("id", this.introspectedTable.getSelectByPrimaryKeyStatementId()));
   /*     if (this.introspectedTable.getRules().generateResultMapWithBLOBs()) {
            answer.addAttribute(new Attribute("resultMap", this.introspectedTable.getResultMapWithBLOBsId()));
        } else {
            answer.addAttribute(new Attribute("resultMap", this.introspectedTable.getBaseResultMapId()));
        }*/
        answer.addAttribute(new Attribute("resultMap", String.valueOf(domainObjectName)+"Map"));
        String parameterType;
        if (this.introspectedTable.getRules().generatePrimaryKeyClass()) {
            parameterType = this.introspectedTable.getPrimaryKeyType();
        } else if (this.introspectedTable.getPrimaryKeyColumns().size() > 1) {
            parameterType = "map";
        } else {
            parameterType = ((IntrospectedColumn)this.introspectedTable.getPrimaryKeyColumns().get(0)).getFullyQualifiedJavaType().toString();
        }

        answer.addAttribute(new Attribute("parameterType", "Integer"));
        this.context.getCommentGenerator().addComment(answer);
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
      /*  if (StringUtility.stringHasValue(this.introspectedTable.getSelectByPrimaryKeyQueryId())) {
            sb.append('\'');
            sb.append(this.introspectedTable.getSelectByPrimaryKeyQueryId());
            sb.append("' as QUERYID,");
        }*/
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(this.introspectedTable.getAllColumns());

/*
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(this.getBaseColumnListElement());
        if (this.introspectedTable.hasBLOBColumns()) {
            answer.addElement(new TextElement(","));
            answer.addElement(this.getBlobColumnListElement());
        }

        sb.setLength(0);*/
        for(int i = 0; i < columns.size(); ++i) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn) columns.get(i);
            sb.append("t.");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            if (i + 1 < columns.size()) {
                sb.append(", ");

            }
        }

        sb.append("  from ");
        sb.append(this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        sb.append("   t");
        answer.addElement(new TextElement(sb.toString()));
        boolean and = false;
        Iterator var6 = this.introspectedTable.getPrimaryKeyColumns().iterator();

        while(var6.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)var6.next();
            sb.setLength(0);
            if (and) {
                sb.append("  and ");
            } else {
                sb.append("where ");
                and = true;
            }
            sb.append("t.");
            sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append("#{");
            sb.append(introspectedColumn.getJavaProperty(null));
            sb.append('}');
           // sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }

        if (this.context.getPlugins().sqlMapSelectByPrimaryKeyElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }

    }
}

