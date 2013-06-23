<?xml version="1.0" encoding="utf-8" ?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="urn:schemas-microsoft-com:office:spreadsheet"
                xmlns:o="urn:schemas-microsoft-com:office:office"
                xmlns:x="urn:schemas-microsoft-com:office:excel"
                xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="
urn:schemas-microsoft-com:office:spreadsheet
urn:schemas-microsoft-com:office:office
urn:schemas-microsoft-com:office:excel
urn:schemas-microsoft-com:office:spreadsheet ">
    <xsl:template match="/div">
        <xsl:processing-instruction name="mso-application">
            <xsl:text>progid="Excel.Sheet"</xsl:text>
        </xsl:processing-instruction>
        <Workbook>
            <Styles>
                <Style ss:ID="Default" ss:Name="Normal">
                    <Alignment ss:Vertical="Bottom"/>
                    <Borders/>
                    <Font/>
                    <Interior/>
                    <NumberFormat/>
                    <Protection/>
                </Style>
            </Styles>
            <Worksheet ss:Name="list">
                <Table>
                    <xsl:apply-templates select="table"/>
                </Table>
            </Worksheet>
        </Workbook>
    </xsl:template>
    <xsl:template match="table">
        <xsl:apply-templates select="thead"/>
        <xsl:apply-templates select="tbody"/>
    </xsl:template>
    <xsl:template match="thead">
        <xsl:apply-templates select="tr"/>
    </xsl:template>
    <xsl:template match="tbody">
        <xsl:apply-templates select="tr"/>
    </xsl:template>
    <xsl:template match="tr">
        <Row>
            <xsl:apply-templates select="th"/>
            <xsl:apply-templates select="td"/>
        </Row>
    </xsl:template>
    <xsl:template match="th|td">
        <Cell>
            <!-- Обработка colspan -->
            <xsl:if test="@colspan">
                <xsl:attribute name="ss:MergeAcross">
                    <xsl:value-of select="@colspan -1"/>
                </xsl:attribute>
            </xsl:if>

            <!-- Обработка rowspan -->
            <xsl:if test="@rowspan">
                <xsl:attribute name="ss:MergeDown">
                    <xsl:value-of select="@rowspan -1"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:call-template name="up">
                <xsl:with-param name="node" select="."/>
                <xsl:with-param name="offset" select="position()"/>
                <xsl:with-param name="position" select="position()"/>
                <xsl:with-param name="rownum" select="count(../preceding-sibling::*) + 1"/>
            </xsl:call-template>

            <Data ss:Type="String">
                <xsl:value-of select="text()"/>
            </Data>
        </Cell>
    </xsl:template>

    <xsl:template name="up">
        <xsl:param name="position"/>
        <xsl:param name="rownum"/>
        <xsl:param name="node"/>
        <xsl:param name="offset"/>
        <xsl:choose>
            <xsl:when test="not(../preceding-sibling::tr/child::*[@rowspan and position() &lt;= $position and @rowspan + count(../preceding-sibling::*) + 1 > $rownum])">
                <xsl:attribute name="ss:Index">
                    <xsl:value-of select="0"/>
                </xsl:attribute>
            </xsl:when>
            <xsl:otherwise>
                <xsl:attribute name="ss:Index">
                    <xsl:value-of select="../preceding-sibling::tr/child::*[@rowspan and position() &lt;= $position and @rowspan + count(../preceding-sibling::*) + 1 > $rownum][0]/@rownum"/>
                </xsl:attribute>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>