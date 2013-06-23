<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

    <xsl:template match="/retrieveReportReturn">
        <div>
            <table border="1" class="params">
                <xsl:apply-templates select="attributes/Pair">
                </xsl:apply-templates>
            </table>
            <div>&#160;</div>
            <!--<xsl:apply-templates select="tables/Table">-->
            <!--</xsl:apply-templates>-->
            <xsl:for-each select="tables/Table">
                <xsl:variable name="i" select="position()"/>
                <xsl:choose>
                <xsl:when test="../../reportOutputTemplate/tables/table[$i]/reportType != ''">
					<xsl:for-each select="../../reportOutputTemplate/tables/table">
					<!-- <xsl:value-of select="../../../attributes/Pair[name='type']/value"/>
					<xsl:value-of select="reportType"/> -->
                    <xsl:if test="reportType = ../../../attributes/Pair[name='type']/value">
                        <div class="retrieveReport-wrapper">
                                        <table border="1" style="width:auto" class="retrieveReport">
                                            <thead class="ui-state-default">
                                                <xsl:choose>
                                                    <xsl:when test="headers/rows/row != ''">
                                                        <xsl:for-each select="headers/rows/row">
                                                            <tr>
                                                                <xsl:for-each select="headerCells/headerCell">
                                                                    <th>
                                                                        <xsl:attribute name="rowspan">
                                                                            <xsl:value-of select="rowspan"/>
                                                                        </xsl:attribute>
                                                                        <xsl:attribute name="colspan">
                                                                            <xsl:value-of select="colspan"/>
                                                                        </xsl:attribute>
                                                                        <xsl:value-of select="headerText"/>
                                                                    </th>
                                                                </xsl:for-each>
                                                            </tr>
                                                        </xsl:for-each>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <tr>
                                                            <xsl:for-each select="../../../../tables/Table[1]/rows/Row[1]/values/string">
                                                                <th></th>
                                                            </xsl:for-each>
                                                        </tr>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </thead>
                                            <tbody>
                                                <xsl:apply-templates select="../../../tables/Table[$i]/rows/Row">
                                                </xsl:apply-templates>
                                            </tbody>
                                        </table>
                        </div>  
                    </xsl:if>
					</xsl:for-each>
                </xsl:when>
                <xsl:otherwise>
                <div class="retrieveReport-wrapper">
                                <table border="1" style="width:auto" class="retrieveReport">
                    <thead class="ui-state-default">
                        <xsl:choose>
                            <xsl:when test="../../reportOutputTemplate/tables/table[$i]/headers/rows/row != ''">
                                <xsl:for-each select="../../reportOutputTemplate/tables/table[$i]/headers/rows/row">
                                    <tr>
                                        <xsl:for-each select="headerCells/headerCell">
                                            <th>
                                                <xsl:attribute name="rowspan">
                                                    <xsl:value-of select="rowspan"/>
                                                </xsl:attribute>
                                                <xsl:attribute name="colspan">
                                                    <xsl:value-of select="colspan"/>
                                                </xsl:attribute>
                                                <xsl:value-of select="headerText"/>
                                            </th>
                                        </xsl:for-each>
                                    </tr>
                                </xsl:for-each>
                            </xsl:when>
                            <xsl:otherwise>
                                <tr>
                                    <xsl:for-each select="rows/Row[1]/values/string">
                                        <th></th>
                                    </xsl:for-each>
                                </tr>
                            </xsl:otherwise>
                        </xsl:choose>

                    </thead>
                    <tbody>
                        <xsl:apply-templates select="rows/Row">
                        </xsl:apply-templates>
                    </tbody>
                </table>
                </div>
                </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
        </div>
    </xsl:template>

    <xsl:template match="Pair">
        <xsl:variable name="name" select="name"/>
        <xsl:variable name="label" select="../../reportOutputTemplate/parameters/parameter[key=$name]/label"/>
        <xsl:if test="$label != ''">
            <tr>
                <td>
                    <xsl:value-of select="$label"/>
                </td>
                <td>
                    <xsl:value-of select="value"/>
                </td>
            </tr>
        </xsl:if>
    </xsl:template>
    <!--<xsl:template match="Table">-->
    <!--<table style="width:auto">-->
    <!--<thead class="ui-state-default">-->
    <!--<xsl:choose>-->
    <!--<xsl:when test="../../reportOutputTemplate/tables/table[1]/headers/rows/row != ''">-->
    <!--<xsl:for-each select="../../reportOutputTemplate/tables/table[1]/headers/rows/row">-->
    <!--<tr>-->
    <!--<xsl:for-each select="headerCells/headerCell">-->
    <!--<th>-->
    <!--<xsl:attribute name="rowspan">-->
    <!--<xsl:value-of select="rowspan"/>-->
    <!--</xsl:attribute>-->
    <!--<xsl:attribute name="colspan">-->
    <!--<xsl:value-of select="colspan"/>-->
    <!--</xsl:attribute>-->
    <!--<xsl:value-of select="headerText"/>-->
    <!--</th>-->
    <!--</xsl:for-each>-->
    <!--</tr>-->
    <!--</xsl:for-each>-->
    <!--</xsl:when>-->
    <!--<xsl:otherwise>-->
    <!--<tr>-->
    <!--<xsl:for-each select="rows/Row[1]/values/string">-->
    <!--<th></th>-->
    <!--</xsl:for-each>-->
    <!--</tr>-->
    <!--</xsl:otherwise>-->
    <!--</xsl:choose>-->

    <!--</thead>-->
    <!--<tbody>-->
    <!--<xsl:apply-templates select="rows/Row">-->
    <!--</xsl:apply-templates>-->
    <!--</tbody>-->
    <!--</table>-->
    <!--</xsl:template>-->
    <xsl:template match="Row">
        <xsl:variable name="i" select="position()"/>
        <tr>
            <xsl:apply-templates select="values/string">
            </xsl:apply-templates>
        </tr>
    </xsl:template>
    <xsl:template match="string">
        <td>
            <xsl:choose>
                <xsl:when test="contains(text(),'|')">
                    <a class="innerReportLink" style="cursor: pointer; text-decoration : underline;"
                       requestParams="{substring-after(text(),'|')}">
                        <xsl:value-of select="substring-before(text(),'|')"/>
                    </a>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="text()"/>
                </xsl:otherwise>
            </xsl:choose>
        </td>
    </xsl:template>
</xsl:stylesheet>


