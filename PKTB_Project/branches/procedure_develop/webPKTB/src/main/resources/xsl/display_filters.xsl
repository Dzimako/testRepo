<?xml version="1.0" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0">

    <xsl:template match="/reportInputTemplate">
        <div id="pktb_project_requestOptions" style="display:none;">
            <xsl:if test="request/databaseOptions != ''">
                <xsl:attribute name="datasource">
                    <xsl:value-of select="request/databaseOptions/dataSource"/>
                </xsl:attribute>
                <xsl:attribute name="sqlStatement">
                    <xsl:value-of select="request/databaseOptions/sqlStatement"/>
                </xsl:attribute>
                <xsl:attribute name="procedureName">
                    <xsl:value-of select="request/databaseOptions/procedureName"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="request/serviceOption != ''">
                <xsl:attribute name="serviceUID">
                    <xsl:value-of select="request/serviceOption/serviceUID"/>
                </xsl:attribute>
            </xsl:if>

        </div>
        <xsl:apply-templates select="filters/filter">
        </xsl:apply-templates>

        <div class='resultTables'>
            <input type="hidden">
                <xsl:attribute name="value">
                    {
                    "tableBeans": [
                    <xsl:apply-templates select="tables/table">
                    </xsl:apply-templates>
                    ]
                    }
                </xsl:attribute>
            </input>
        </div>

        <div class='dictionaries'>
            <input type="hidden">
                <xsl:attribute name="value">
                    {
                    "dictionaries": [
                    <xsl:apply-templates select="dictionaries/dictionary">
                    </xsl:apply-templates>
                    ]
                    }
                </xsl:attribute>
            </input>
        </div>

        <div class='importedDictionaries'>
            <input type="hidden">
                <xsl:attribute name="value">
                    {
                    "importedDictionaries": [
                    <!--<xsl:apply-templates select="importdictionaries/dictionaryKey">
                    </xsl:apply-templates>-->
                    <xsl:for-each select="importDictionaries/dictionaryKey">"<xsl:value-of select="text()"/>"<xsl:if test="following-sibling::*">,</xsl:if></xsl:for-each>
                    ]
                    }
                </xsl:attribute>
            </input>
        </div>

        <div class='possibleRequests'>
                    <input type="hidden">
                        <xsl:attribute name="value">
                            {
                            "resultSets": [
                            <xsl:apply-templates select="possibleRequests/possibleRequest">
                            </xsl:apply-templates>
                            <!--<xsl:for-each select="possibleRequests/possibleRequest">"<xsl:value-of select="text()"/>"<xsl:if test="following-sibling::*">,</xsl:if></xsl:for-each>-->
                            ]
                            }
                        </xsl:attribute>
                    </input>
                </div>

    </xsl:template>

    <xsl:template match="filter">
        <div class="filterItem">
            <xsl:attribute name="paramKey">
                <xsl:value-of select="key"/>
            </xsl:attribute>
            <xsl:attribute name="orderNum">
                <xsl:value-of select="paramOrderNum"/>
            </xsl:attribute>
            <xsl:attribute name="paramType">
                <xsl:value-of select="paramType"/>
            </xsl:attribute>
            <xsl:attribute name="markerSQLCode">
                <xsl:value-of select="markerSQLCode"/>
            </xsl:attribute>
            <xsl:attribute name="paramDataType">
                <xsl:value-of select="paramDataType"/>
            </xsl:attribute>
            <xsl:if test="type = 'HIDDEN'">
                <xsl:attribute name="class">
                    <xsl:value-of select="type"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="validation != ''">
                <xsl:attribute name="validation">
                    {"type" : "<xsl:value-of select="validation/type"/>"}
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="switchParameter != ''">
                <xsl:attribute name="switchType">
                    <xsl:value-of select="switchParameter/switchType"/>
                </xsl:attribute>
                <xsl:attribute name="switchRelativeFilter">
                    <xsl:value-of select="switchParameter/relativeFilterKey"/>
                </xsl:attribute>
                <xsl:attribute name="switchparams">
                    {"includeValues" : [
                    <xsl:for-each select="switchParameter/includeValueKeys/includeKey">
                        "<xsl:value-of select="text()"/>"
                        <xsl:if test="following-sibling::*">,</xsl:if>
                    </xsl:for-each>
                    ], "excludeValues" : [
                    <xsl:for-each select="switchParameter/excludeValueKeys/excludeKey">
                        "<xsl:value-of select="text()"/>"
                        <xsl:if test="following-sibling::*">,</xsl:if>
                    </xsl:for-each>
                    ]}
                </xsl:attribute>
            </xsl:if>

            <xsl:if test="changeKeyOptions != ''">
                <xsl:attribute name="relativeFilterKey">
                    <xsl:apply-templates select="changeKeyOptions/relativeFilterKey"/>
                </xsl:attribute>
                <xsl:attribute name="changeKeyOptions">
                    [
                    <xsl:for-each select="changeKeyOptions/valueKeyDependencies/valueKeyDependency">
                        {"relativeFilterValue": "<xsl:value-of select="relativeFilterValue"/>", "keyToSwitch":
                        "<xsl:value-of select="keyToSwitch"/>"}
                        <xsl:if test="following-sibling::*">,</xsl:if>
                    </xsl:for-each>
                    ]
                </xsl:attribute>
            </xsl:if>
            <label>
                <xsl:value-of select="label"/>
            </label>
            <xsl:if test="(type = 'SELECT') or type = ('SELECT_WITH_PARAMS')">
                <select class="input">
                    <xsl:if test="requestAdress/databaseOptions != ''">
                        <xsl:attribute name="databaseAdress">
                            <xsl:value-of select="requestAdress/databaseOptions/dataSource"/>
                        </xsl:attribute>
                        <xsl:attribute name="filterDBkey">
                            <xsl:value-of select="requestAdress/request/filterDBkey"/>
                        </xsl:attribute>
                        <xsl:attribute name="databaseTable">
                            <xsl:value-of select="requestAdress/request/databaseTable"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:if test="requestAdress/serviceOption">
                        <xsl:attribute name="serviceUID">
                            <xsl:value-of select="requestAdress/serviceOption/serviceUID"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:if test="userDefaultRW = 'true'">
                        <xsl:attribute name="setUsersRW">
                            <xsl:value-of select="userDefaultRW"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:apply-templates select="filterValues/filterValue">
                    </xsl:apply-templates>
                </select>
            </xsl:if>
            <xsl:if test="type = 'INPUT'">
                <input type="text" class="input">
                    <xsl:attribute name="value">
                        <xsl:if test="userDefaultRW = 'true'">
                            <xsl:attribute name="setUsersRW">
                                <xsl:value-of select="userDefaultRW"/>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:value-of select="defaultValue"/>
                    </xsl:attribute>
                </input>
            </xsl:if>
            <xsl:if test="type = 'CHECKBOX'">
                <input type="checkbox"/>
            </xsl:if>
            <!--<xsl:if test="type = 'DATEPICKER_SIMPLE'">
                <input type="text" class="datePickerInput_simple">
                    <xsl:attribute name="value">
                        <xsl:value-of select="defaultValue"/>
                    </xsl:attribute>
                </input>
            </xsl:if>-->
            <xsl:if test="type = 'DATEPICKER'">
                <input type="text">
                    <xsl:choose>
                        <xsl:when test="showTime != ''">
                            <xsl:attribute name="class">datePickerInput_withTime input</xsl:attribute>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:attribute name="class">datePickerInput input</xsl:attribute>
                        </xsl:otherwise>
                    </xsl:choose>
                    <xsl:attribute name="defaultValue">
                        <xsl:value-of select="defaultValue"/>
                    </xsl:attribute>
                    <xsl:if test="currentTime = 'true'">
                        <xsl:attribute name="setCurrentTime">setCurrentTime</xsl:attribute>
                    </xsl:if>
                    <xsl:if test="dateParamKeys != ''">
                        <xsl:if test="dateParamKeys/dateKey != ''">
                            <xsl:attribute name="param_date">
                                <xsl:value-of select="dateParamKeys/dateKey"/>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:if test="dateParamKeys/yearKey != ''">
                            <xsl:attribute name="param_year">
                                <xsl:value-of select="dateParamKeys/yearKey"/>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:if test="dateParamKeys/monthKey != ''">
                            <xsl:attribute name="param_month">
                                <xsl:value-of select="dateParamKeys/monthKey"/>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:if test="dateParamKeys/dayKey != ''">
                            <xsl:attribute name="param_day">
                                <xsl:value-of select="dateParamKeys/dayKey"/>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:if test="dateParamKeys/hourKey != ''">
                            <xsl:attribute name="param_hour">
                                <xsl:value-of select="dateParamKeys/hourKey"/>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:if test="dateParamKeys/minuteKey != ''">
                            <xsl:attribute name="param_minute">
                                <xsl:value-of select="dateParamKeys/minuteKey"/>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:if test="dateParamKeys/dateFormat != ''">
                            <xsl:attribute name="dateFormat">
                                <xsl:value-of select="dateParamKeys/dateFormat"/>
                            </xsl:attribute>
                        </xsl:if>
                    </xsl:if>
                </input>
            </xsl:if>
            <xsl:if test="type = 'TIMEPICKER'">
                <input type="text" class="timePickerInput input">
                    <xsl:attribute name="defaultValue">
                        <xsl:value-of select="defaultValue"/>
                    </xsl:attribute>
                    <xsl:if test="dateParamKeys != ''">
                        <xsl:if test="dateParamKeys/hourKey != ''">
                            <xsl:attribute name="param_hour">
                                <xsl:value-of select="dateParamKeys/hourKey"/>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:if test="dateParamKeys/minuteKey != ''">
                            <xsl:attribute name="param_minute">
                                <xsl:value-of select="dateParamKeys/minuteKey"/>
                            </xsl:attribute>
                        </xsl:if>
                    </xsl:if>
                </input>
            </xsl:if>
            <!--<xsl:if test="type = 'DATEPICKER'">
                <input type="text" class="datePickerInput">
                    <xsl:attribute name="value">
                        <xsl:value-of select="defaultValue"/>
                    </xsl:attribute>
                </input>
            </xsl:if>-->
            <xsl:if test="type = 'HIDDEN'">
                <input type="hidden">
                    <xsl:if test="userDefaultRW  = 'true'">
                        <xsl:attribute name="setUsersRW">
                            <xsl:value-of select="userDefaultRW"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:attribute name="value">
                        <xsl:value-of select="defaultValue"/>
                    </xsl:attribute>
                </input>
            </xsl:if>
        </div>

    </xsl:template>


    <xsl:template match="filterValue">
        <option>
            <xsl:if test="selectParams/paramItem != ''">
                <xsl:attribute name="paramMap">
                    [
                    <xsl:apply-templates select="selectParams/paramItem"/>
                    ]
                </xsl:attribute>
            </xsl:if>
            <xsl:attribute name="value">
                <xsl:value-of select="paramValue"/>
            </xsl:attribute>
            <xsl:value-of select="label"/>
        </option>
    </xsl:template>

    <xsl:template match="paramItem">
        {
        "key":          "<xsl:value-of select="key"/>",
        "paramValue":   "<xsl:value-of select="paramValue"/>"
        }
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="table">
        {
        "inputResultSet" : { "key" : "<xsl:value-of select="inputResultSet/key"/>"},
        "outputResultSet" : { "key" : "<xsl:value-of select="outputResultSet/key"/>"},
        "columns" : [
        <xsl:apply-templates select="columns/column">
        </xsl:apply-templates>
        ],
        "rowFunctions" : [
        <xsl:apply-templates select="rowFunctions/rowFunction">
                </xsl:apply-templates>
        ]
        }
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="rowFunction">
            {
            "operator":
            <xsl:apply-templates select="operator">
            </xsl:apply-templates>,
            "columns": [
                    <xsl:apply-templates select="columns/column">
                    </xsl:apply-templates>
            ],
            "rowFunctions" : [
                <xsl:apply-templates select="rowFunctions/rowFunction">
                        </xsl:apply-templates>
                ],
        "resultSetToUse" : {"key" : "<xsl:value-of select="resultSetToUse/key"/>"},

        "repeatColumns" : [
        <xsl:apply-templates select="columnsToRepeat/columnToRepeat">
                    </xsl:apply-templates>
        ]}
            <xsl:if test="following-sibling::*">,</xsl:if>
        </xsl:template>

    <xsl:template match="column">
        {
        "operator":
        <xsl:apply-templates select="operator">
        </xsl:apply-templates>
        }
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="operator">
        {
        <xsl:if test="selectColumnRule !=''">
                            "columnRuleBean":
                    <xsl:apply-templates select="selectColumnRule">
                                </xsl:apply-templates>,
                        </xsl:if>
        "operatorKey":"<xsl:value-of select="operatorKey"/>"
        <xsl:if test="arguments/argument !=''">
            ,"arguments": [
            <xsl:apply-templates select="arguments/argument">
            </xsl:apply-templates>
            ]
        </xsl:if>

        }
    </xsl:template>

    <xsl:template match="argument">
        {
        <xsl:if test="defaultValue !=''">
            "defaultValue":
            "<xsl:value-of select="defaultValue"/>"   <xsl:if test="((columnNumber !='') or (operator != ''))">,</xsl:if>
        </xsl:if>
        <xsl:choose>
            <xsl:when test="columnNumber !=''">
                "columnNumber":
                "<xsl:value-of select="columnNumber"/>"
            </xsl:when>
            <xsl:otherwise>
                <xsl:if test="operator !=''">
                    "operator":
                    <xsl:apply-templates select="operator">
                    </xsl:apply-templates>
                </xsl:if>
            </xsl:otherwise>
        </xsl:choose>
        }<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="dictionary">
        {
        "dataSource": "<xsl:value-of select="dataSource"/>",
        "filterDBkey":"<xsl:value-of select="filterDBkey"/>",
        "filterDBkeyValue":"<xsl:value-of select="filterDBkeyValue"/>",
        "databaseTable":"<xsl:value-of select="databaseTable"/>",
        "dictionaryKey":"<xsl:value-of select="dictionaryKey"/>",
        "exceptions" : [
        <xsl:apply-templates select="exceptions/exception">
        </xsl:apply-templates>
        ]
        }
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="exception">
        {
        "key": "<xsl:value-of select="key"/>",
        "value":"<xsl:value-of select="value"/>"
        }
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="possibleRequest">
        {
        "key": "<xsl:value-of select="databaseOptions/requestKey"/>",
        "datasource":"<xsl:value-of select="databaseOptions/dataSource"/>",
        "sqlStatement":"<xsl:value-of select="databaseOptions/sqlStatement"/>",
        "proc_name":"<xsl:value-of select="databaseOptions/procedureName"/>",
        "params": [<xsl:apply-templates select="parameters/parameter">
    </xsl:apply-templates>]
        }
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="parameter">
        {
        "name": "<xsl:value-of select="key"/>",
        "value":"<xsl:value-of select="defaultValue"/>",
        "inputParam":"<xsl:value-of select="paramType"/>",
        "orderNum":"<xsl:value-of select="paramOrderNum"/>",
        "paramDataType":"<xsl:value-of select="paramDataType"/>"
        }
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="selectColumnRule">
            {
            "formulaBean": <xsl:apply-templates select="columnNumFormula">
                            </xsl:apply-templates>,
            "certainColumns":[<xsl:apply-templates select="certainColumns/certainColumn">
                </xsl:apply-templates>]
            }
        </xsl:template>

    <xsl:template match="certainColumn">
                <xsl:value-of select="."/>
                <xsl:if test="following-sibling::*">,</xsl:if>
            </xsl:template>

    <xsl:template match="columnNumFormula">
                {    <xsl:if test="fixedPosition != ''">"fixedPosition" : "<xsl:value-of select="fixedPosition"/>",</xsl:if>

        <xsl:if test="singleValue != ''">"singleValue" : "<xsl:value-of select="singleValue"/>",</xsl:if>
        <xsl:if test="numToBegin != ''">"numToBegin": "<xsl:value-of select="numToBegin"/>",</xsl:if>
        <xsl:if test="numToEnd != ''">"numToEnd":"<xsl:value-of select="numToEnd"/>",</xsl:if>
        <xsl:if test="formulaString != ''">"formulaString":"<xsl:value-of select="formulaString"/>"</xsl:if>
                }
                <xsl:if test="following-sibling::*">,</xsl:if>
            </xsl:template>


    <xsl:template match="columnToRepeat">
                    {
            "columnIndexesRule" : <xsl:apply-templates select="selectColumnRule">
                                                    </xsl:apply-templates>,
            "column" : <xsl:apply-templates select="column">
                                        </xsl:apply-templates>
                    }
                    <xsl:if test="following-sibling::*">,</xsl:if>
                </xsl:template>

</xsl:stylesheet>


