package com.digdes.pktb.persistence.util;

import com.digdes.pktb.persistence.beans.ajaxbeans.outputModel.*;
import com.digdes.pktb.persistence.beans.xml.RowXML;
import org.apache.log4j.Logger;

import java.lang.Exception;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Calculator {
    private static Logger logger = Logger.getLogger("debug-log-file");
    private ProcedureResultSet resultSet;
    private Map<String, Map<String, String>> mapDictionariesByKey;

    public Calculator(ProcedureResultSet resultSet, Map<String, Map<String, String>> mapDictionariesByKey) {
        this.resultSet = resultSet;
        this.mapDictionariesByKey = mapDictionariesByKey;
    }

    public ProcedureResultSet getResultSet() {
        return resultSet;
    }

    public Map<String, Map<String, String>> getMapDictionariesByKey() {
        return mapDictionariesByKey;
    }

    public String calculateCellValue(OperatorBean operatorBean, Integer rowIndex, String prevValue) {
        Calculator.logger.debug("OperatorBean: " + operatorBean);
//        if(operatorBean != null){
        OperatorType operatorType = OperatorType.getType(operatorBean.getOperatorKey());
        switch (operatorType) {
            case SIMPLE_COLUMN:
                return getSimpleColumnValue(operatorBean, rowIndex);
            case ADD_INTEGERS:
                return getAdditionResultValue(operatorBean, rowIndex);
            case SUBTRACTION_INTEGERS:
                return getSubtractionResultValue(operatorBean, rowIndex);
            case MULTIPLY_INTEGERS:
                return getMultiplicationResultValue(operatorBean, rowIndex);
            case DIVIDE_INTEGERS:
                return getDivisionResultValue(operatorBean, rowIndex);
            case ADD_STRINGS:
                return getAddingStringsResultValue(operatorBean, rowIndex);
            case ITERATION:
                return getIterationRowIndex(rowIndex);
            case CHECK_ON_DEFAULT_VALUE:
                return checkOnDefaultValue(operatorBean, rowIndex);
            case DICTIONARY:
                return dictionary(operatorBean, rowIndex);
            case TRANSFORM_TIME_FROM_DATE:
                return transformTimeFromDate(operatorBean, rowIndex);
            case IF_THEN_ELSE:
                String testBool = executeIfThenElse(operatorBean, rowIndex);
                logger.debug("Result of if_then_else = " + testBool);
                return testBool;//executeIfThenElse(operatorBean, rowIndex);
            case BOOLEAN_OR:
                return executeBooleanOr(operatorBean, rowIndex);
            case BOOLEAN_AND:
                return executeBooleanAnd(operatorBean, rowIndex);
            case BOOLEAN_NOT:
                return executeBooleanNot(operatorBean, rowIndex);
            case BOOLEAN_EQUAL:
                return executeBooleanEqual(operatorBean, rowIndex);
            case BOOLEAN_LESS:
                return executeBooleanLess(operatorBean, rowIndex);
            case BOOLEAN_LESS_OR_EQUAL:
                return executeBooleanLessOrEqual(operatorBean, rowIndex);
            case BOOLEAN_GREATER:
                return executeBooleanGreater(operatorBean, rowIndex);
            case BOOLEAN_GREATER_OR_EQUAL:
                return executeBooleanGreaterOrEqual(operatorBean, rowIndex);
            case ADD_PREVIOUS_VALUES:
                return addPreviousValue(operatorBean, rowIndex, prevValue);
//        }
        }
        return "";
    }

    public List<RowXML> calculateRowFunctionRows(RowFunctionBean rowFunctionBean) {
        if (rowFunctionBean.getOperatorBean() != null && rowFunctionBean.getOperatorBean().getOperatorKey() != null) {
            RowFunctionType rowFunctionType = RowFunctionType.getType(rowFunctionBean.getOperatorBean().getOperatorKey());
            switch (rowFunctionType) {
                case TOTAL_BY_UNIQUE:
                    return getTotalByUnique(rowFunctionBean);
                case MAKE_DYNAMIC_HEADERS:
                    return makingDynamicColumnHeaders(rowFunctionBean);
                case MAKE_REPORT_WITH_UNIQUE_HEADERS:
                    return makeReportWithUniqueHeaders(rowFunctionBean);
            }
        }
        return new ArrayList<RowXML>();
    }

    /*
* Метод выполняет простейшее вычисление.
* Предполагается что на вход подается только один аргумент, соответственно этот аргумент вычисляется и возвращается.*/

    private String getSimpleColumnValue(OperatorBean operatorBean, Integer rowIndex) {
        if (operatorBean.getArguments() != null && operatorBean.getArguments().size() > 0) {
            ArgumentBean argumentBean = operatorBean.getArguments().get(0);
            return getArgumentValue(argumentBean, rowIndex);

        }
        return "";
    }

    /*
* Метод выполняет суммирование аргументов.
* Все аргументы оператора преобразуются в int и суммируются.
* Результат суммирования возвращается ввиде строки*/

    private String getAdditionResultValue(OperatorBean operatorBean, Integer rowIndex) {
        Integer additionResult = 0;
        if (operatorBean.getArguments() != null) {
            for (ArgumentBean argumentBean : operatorBean.getArguments()) {
                additionResult += Integer.parseInt(getArgumentValue(argumentBean, rowIndex));
            }
        }
        return additionResult.toString();
    }

    /*
* Метод выполняет вычитание аргументов
* Каждый аргумент в ходе вычислений преобразуется в int.
* Вычисляется первый аргумент. После чего из него вычитаются все остальные аргументы
* Результат умножения возвращается ввиде строки*/

    private String getSubtractionResultValue(OperatorBean operatorBean, Integer rowIndex) {
        Integer subtractionResult = null;
        if (operatorBean.getArguments() != null) {
            for (ArgumentBean argumentBean : operatorBean.getArguments()) {
                Integer subtracting = Integer.parseInt(getArgumentValue(argumentBean, rowIndex));
                if (subtractionResult == null) {
                    subtractionResult = subtracting;
                } else {
                    subtractionResult -= subtracting;
                }
            }
        }
        if (subtractionResult != null)
            return subtractionResult.toString();
        return "";
    }

    /*
* Метод выполняет умножение аргументов.
* Каждый аргумент в ходе вычислений преобразуется в int и перемножаются.
* Результат умножения возвращается ввиде строки*/

    private String getMultiplicationResultValue(OperatorBean operatorBean, Integer rowIndex) {
        Integer multiplicationResult = null;
        for (ArgumentBean argumentBean : operatorBean.getArguments()) {
            Integer multiplying = Integer.parseInt(getArgumentValue(argumentBean, rowIndex));
            if (multiplicationResult == null) {
                multiplicationResult = multiplying;
            } else {
                multiplicationResult *= multiplying;
            }
        }
        if (multiplicationResult != null)
            return multiplicationResult.toString();
        return "";
    }

    /*
* Метод выполняет вычитание аргументов
* Каждый аргумент в ходе вычислений преобразуется в int.
* Вычисляется первый аргумент. После чего он делится на каждый последующий аргумент
* Результат умножения возвращается ввиде строки*/

    private String getDivisionResultValue(OperatorBean operatorBean, Integer rowIndex) {
        Integer divisionResult = null;
        for (ArgumentBean argumentBean : operatorBean.getArguments()) {
            Integer divisor = null;
            try {
                divisor = Integer.parseInt(getArgumentValue(argumentBean, rowIndex));
            } catch (NumberFormatException e) {
                logger.debug("getDivisionResultValue() divisor parse NumberFormatException");
                return "";
            }
            if (divisionResult == null) {
                divisionResult = divisor;
            } else if (divisor != 0) {
                divisionResult /= divisor;
            }
        }
        if (divisionResult != null)
            return divisionResult.toString();
        return "";
    }

    /*
* Метод складывает строки
* Вычисляются значения всех аргументов ввиде строк и складываются вместе*/

    private String getAddingStringsResultValue(OperatorBean operatorBean, Integer rowIndex) {
        StringBuilder stringResult = new StringBuilder();
        for (ArgumentBean argumentBean : operatorBean.getArguments()) {
            stringResult.append(getArgumentValue(argumentBean, rowIndex));
        }
        return stringResult.toString();
    }

    /*
    * Метод возвращает индекс итерации*/

    private String getIterationRowIndex(Integer rowIndex) {
        if (rowIndex != null) {
            return rowIndex.toString();
        }
        return "";
    }

    /*
    *Метод проверяет на равенство парвый аргумен и второй. Если они равны, то возвращаем значение третьего*/

    private String checkOnDefaultValue(OperatorBean operatorBean, Integer rowIndex) {
        if (operatorBean.getArguments() != null && operatorBean.getArguments().size() > 0) {
            return getArgumentValue(operatorBean.getArguments().get(0), rowIndex).equals(getArgumentValue(operatorBean.getArguments().get(1), rowIndex))
                    ? getArgumentValue(operatorBean.getArguments().get(2), rowIndex)
                    : getArgumentValue(operatorBean.getArguments().get(0), rowIndex);
        }
        return "";
    }

    /*
     *Метод реализует выражение if-then-else. Первый параметр - булеановское выражение, второй и третий - then и else, соответственно*/

    private String executeIfThenElse(OperatorBean operatorBean, Integer rowIndex) {
        if (operatorBean.getArguments() != null && operatorBean.getArguments().size() > 0) {
            return Boolean.parseBoolean(getArgumentValue(operatorBean.getArguments().get(0), rowIndex))
                    ? getArgumentValue(operatorBean.getArguments().get(1), rowIndex)
                    : getArgumentValue(operatorBean.getArguments().get(2), rowIndex);
        }
        return "";
    }

    /*
     *Метод реализует оператор ||.*/

    private String executeBooleanOr(OperatorBean operatorBean, Integer rowIndex) {
        if (operatorBean.getArguments() != null && operatorBean.getArguments().size() > 0) {
            return Boolean.toString(Boolean.parseBoolean(getArgumentValue(operatorBean.getArguments().get(0), rowIndex)) || Boolean.parseBoolean(getArgumentValue(operatorBean.getArguments().get(1), rowIndex)));
        }
        return "";
    }

    /*
     *Метод реализует оператор &&.*/

    private String executeBooleanAnd(OperatorBean operatorBean, Integer rowIndex) {
        if (operatorBean.getArguments() != null && operatorBean.getArguments().size() > 0) {
            return Boolean.toString(Boolean.parseBoolean(getArgumentValue(operatorBean.getArguments().get(0), rowIndex)) && Boolean.parseBoolean(getArgumentValue(operatorBean.getArguments().get(1), rowIndex)));
        }
        return "";
    }

    /*
     *Метод реализует оператор !.*/

    private String executeBooleanNot(OperatorBean operatorBean, Integer rowIndex) {
        if (operatorBean.getArguments() != null && operatorBean.getArguments().size() > 0) {
            return Boolean.toString(!Boolean.parseBoolean(getArgumentValue(operatorBean.getArguments().get(0), rowIndex)));
        }
        return "";
    }

    /*
     *Метод реализует оператор ==.*/

    private String executeBooleanEqual(OperatorBean operatorBean, Integer rowIndex) {
        if (operatorBean.getArguments() != null && operatorBean.getArguments().size() > 0) {
            return Boolean.toString(getArgumentValue(operatorBean.getArguments().get(0), rowIndex).equals(getArgumentValue(operatorBean.getArguments().get(1), rowIndex)));
        }
        return "";
    }

    /*
     *Метод реализует оператор <.*/

    private String executeBooleanLess(OperatorBean operatorBean, Integer rowIndex) {
        if (operatorBean.getArguments() != null && operatorBean.getArguments().size() > 0) {
            try {
                return Boolean.toString(Double.parseDouble(getArgumentValue(operatorBean.getArguments().get(0), rowIndex)) < Double.parseDouble(getArgumentValue(operatorBean.getArguments().get(1), rowIndex)));
            } catch (NumberFormatException e) {
                logger.debug("executeBooleanLess() NumberFormatException: ", e);
            }
        }
        return "";
    }

    /*
     *Метод реализует оператор <=.*/

    private String executeBooleanLessOrEqual(OperatorBean operatorBean, Integer rowIndex) {
        if (operatorBean.getArguments() != null && operatorBean.getArguments().size() > 0) {
            try {
                return Boolean.toString(Double.parseDouble(getArgumentValue(operatorBean.getArguments().get(0), rowIndex)) <= Double.parseDouble(getArgumentValue(operatorBean.getArguments().get(1), rowIndex)));
            } catch (NumberFormatException e) {
                logger.debug("executeBooleanLessOrEqual() NumberFormatException: ", e);
            }
        }
        return "";
    }

    /*
     *Метод реализует оператор >.*/

    private String executeBooleanGreater(OperatorBean operatorBean, Integer rowIndex) {
        if (operatorBean.getArguments() != null && operatorBean.getArguments().size() > 0) {
            try {
                return Boolean.toString(Double.parseDouble(getArgumentValue(operatorBean.getArguments().get(0), rowIndex)) > Double.parseDouble(getArgumentValue(operatorBean.getArguments().get(1), rowIndex)));
            } catch (NumberFormatException e) {
                logger.debug("executeBooleanGreater() NumberFormatException: ", e);
            }
        }
        return "";
    }

    /*
     *Метод реализует оператор >.*/

    private String executeBooleanGreaterOrEqual(OperatorBean operatorBean, Integer rowIndex) {
        if (operatorBean.getArguments() != null && operatorBean.getArguments().size() > 0) {
            try {
                return Boolean.toString(Double.parseDouble(getArgumentValue(operatorBean.getArguments().get(0), rowIndex)) >= Double.parseDouble(getArgumentValue(operatorBean.getArguments().get(1), rowIndex)));
            } catch (NumberFormatException e) {
                logger.debug("executeBooleanGreaterOrEqual() NumberFormatException: ", e);
            }
        }
        return "";
    }

    /*
    *Метод возвращает значение из словаря. Первый агрумент - ключ словаря. Второй - ключ поля в словаре.*/

    private String dictionary(OperatorBean operatorBean, Integer rowIndex) {
        if (operatorBean.getArguments() != null && operatorBean.getArguments().size() > 0) {
//            logger.debug("getMapDictionariesByKey(): " + getMapDictionariesByKey());
//            logger.debug("getMapDictionariesByKey().get(getArgumentValue(operatorBean.getArguments().get(0), rowIndex)): " + (getMapDictionariesByKey().get(getArgumentValue(operatorBean.getArguments().get(0), rowIndex)) == null) + " by key: " + getArgumentValue(operatorBean.getArguments().get(0), rowIndex));
//            logger.debug("keys: ");
//            for (String key : getMapDictionariesByKey().keySet()) {
//                logger.debug(key);
//            }
            return getMapDictionariesByKey().get(getArgumentValue(operatorBean.getArguments().get(0), rowIndex).trim()).get(getArgumentValue(operatorBean.getArguments().get(1), rowIndex).trim());
        }
        return "";
    }

    private String transformTimeFromDate(OperatorBean operatorBean, Integer rowIndex) {
        if (operatorBean.getArguments() != null && operatorBean.getArguments().size() > 0) {
            DateFormat dateFormat = new SimpleDateFormat(getArgumentValue(operatorBean.getArguments().get(1), rowIndex));
            try {
                Date date = dateFormat.parse(getArgumentValue(operatorBean.getArguments().get(0), rowIndex));
                return new SimpleDateFormat(getArgumentValue(operatorBean.getArguments().get(2), rowIndex)).format(date);
            } catch (ParseException e) {
                logger.error("transformTimeFromDate()", e);
            }
        }
        return "";
    }


    private String addPreviousValue(OperatorBean operatorBean, Integer rowIndex, String prevValue){
        String currentValue = getAdditionResultValue(operatorBean, rowIndex);
        if(prevValue != null){
            currentValue =  (Integer.parseInt(currentValue) + Integer.parseInt(prevValue)) + "";
        }
        return currentValue;
    }

    private String getArgumentValue(ArgumentBean argumentBean, Integer rowIndex) {
        if (argumentBean.getOperator() != null) {
            return calculateCellValue(argumentBean.getOperator(), rowIndex, null);
        } else if (argumentBean.getDefaultValue() != null) {
            return argumentBean.getDefaultValue();
        } else if (argumentBean.getColumnNumber() != null) {
            return resultSet.getColumnValue(argumentBean.getColumnNumber());
        }
        return "";
    }


//=============================================Функции для вычисления строк (rowFunctions)========================================

    /*
    * Метод позволяет строить строки которые могут:
     *      -- суммировать значения по столбцам разных строк
     *      -- если задан первый аргумент(столдец уникальных элементов), то суммирования будут проходить по блокам уникальности
     *      -- если первый параметр не задан просуммируются все строки
     *      -- считать количество вхождения уникальных элементов из первого аргумента*/

    private List<RowXML> getTotalByUnique(RowFunctionBean rowFunctionBean) {
        //        List<RowXML> rowXMLs = new ArrayList<RowXML>();
        Map<String, RowXML> rowXMLMap = new HashMap<String, RowXML>();
        ArgumentBean uniqueFieldArg = null;
        if (rowFunctionBean.getOperatorBean() != null && rowFunctionBean.getOperatorBean().getArguments() != null && rowFunctionBean.getOperatorBean().getArguments().size() > 0) {
            uniqueFieldArg = rowFunctionBean.getOperatorBean().getArguments().get(0);
        }
        resultSet.beforeFirst();
        int rowNumber = 1;
        logger.debug("Row function == Total_unique: " + rowFunctionBean);
        while (resultSet.next()) {
            String uniqueFieldValue = "default";
            if (uniqueFieldArg != null) {
                uniqueFieldValue = getArgumentValue(uniqueFieldArg, rowNumber);
            }
            RowXML rowXML = null;
            if (uniqueFieldArg != null && (uniqueFieldArg.getDefaultValue() == null || uniqueFieldArg.getDefaultValue().equalsIgnoreCase(uniqueFieldValue))) {
                rowXML = rowXMLMap.get(uniqueFieldValue);
            }
            if (rowXML == null) {
                rowXML = new RowXML(new ArrayList<String>());
            }
            addColumnBeanValues(rowFunctionBean, rowXML, rowNumber);
            /*if (rowFunctionBean.getColumnBeans() != null && rowFunctionBean.getColumnBeans().size() > 0) {
                int columnNumber = 1;
                for (ColumnBean columnBean : rowFunctionBean.getColumnBeans()) {
                    String valueToUpdate;
                    if (rowXML.getValues() != null && rowXML.getValues().size() > columnNumber) {
                        valueToUpdate = calculateCellValue(columnBean.getOperator(), rowNumber, rowXML.getValues().get(columnNumber));//Integer.parseInt(rowXML.getValues().get(columnNumber)) + Integer.parseInt(valueFromResult) + "";
                    } else {
                        valueToUpdate = calculateCellValue(columnBean.getOperator(), rowNumber, null);
                    }
                    putValueToRow(rowXML, valueToUpdate, columnNumber);
                    columnNumber++;
                }
            }*/
            rowXMLMap.put(uniqueFieldValue, rowXML);
//            }
            rowNumber++;
        }
        return new ArrayList<RowXML>(rowXMLMap.values());
    }

    /*private Boolean toAddColumnValues(List<ArgumentBean> argumentBeans, Integer columnNumber) {
        if (argumentBeans != null) {
            for (ArgumentBean argumentBean : argumentBeans) {
                if (argumentBean.getColumnNumber() != null && argumentBean.getColumnNumber().equals(columnNumber) && argumentBeans.indexOf(argumentBean) > 0) {
                    return true;
                }
            }
        }
        return false;
    }*/

    private void putValueToRow(RowXML rowXML, String valueToUpdate, Integer columnNum, String defaultValue) {
        if (rowXML != null && rowXML.getValues() != null) {
            if (rowXML.getValues().size() > columnNum) {
                rowXML.getValues().set(columnNum, valueToUpdate);
            } else {
                Integer columnsToAdd = columnNum - rowXML.getValues().size();
                while (columnsToAdd > 0) {
                    rowXML.getValues().add(defaultValue);
                    columnsToAdd--;
                }
                rowXML.getValues().add(valueToUpdate);

            }
        }
    }


    private List<RowXML> makingDynamicColumnHeaders(RowFunctionBean rowFunctionBean){
        List<RowXML> rowXMLs = new ArrayList<RowXML>();
        ArgumentBean uniqueFieldArg = null;
                if (rowFunctionBean.getOperatorBean() != null && rowFunctionBean.getOperatorBean().getArguments() != null && rowFunctionBean.getOperatorBean().getArguments().size() > 0) {
                    uniqueFieldArg = rowFunctionBean.getOperatorBean().getArguments().get(0);
                }
        if(uniqueFieldArg != null){
            RowXML rowXML =  new RowXML(new ArrayList<String>());

        resultSet.beforeFirst();
                int rowNumber = 1;
            addColumnBeanValues(rowFunctionBean, rowXML, rowNumber);
//                logger.debug("Row function == Total_unique: " + rowFunctionBean);
            Set<String> uniqueValues = new HashSet<String>();
                while (resultSet.next()) {
                    String uniqueFieldValue = getArgumentValue(uniqueFieldArg, rowNumber);
                    uniqueValues.add(uniqueFieldValue);
                    rowNumber++;
                }
            for (String uniqueValue : uniqueValues){
                rowXML.getValues().add(uniqueValue);
            }
            rowXMLs.add(rowXML);
        }
        return rowXMLs;
    }

    private void addColumnBeanValues(RowFunctionBean rowFunctionBean, RowXML rowXML, Integer rowNumber) {
        if (rowFunctionBean.getColumnBeans() != null && rowFunctionBean.getColumnBeans().size() > 0) {
            int columnNumber = 1;
            for (ColumnBean columnBean : rowFunctionBean.getColumnBeans()) {
                String valueToUpdate;
                if (rowXML.getValues() != null && rowXML.getValues().size() > columnNumber) {
                    valueToUpdate = calculateCellValue(columnBean.getOperator(), rowNumber, rowXML.getValues().get(columnNumber));//Integer.parseInt(rowXML.getValues().get(columnNumber)) + Integer.parseInt(valueFromResult) + "";
                } else {
                    valueToUpdate = calculateCellValue(columnBean.getOperator(), rowNumber, null);
                }
                putValueToRow(rowXML, valueToUpdate, columnNumber, "");
                columnNumber++;
            }
        }
    }

    private List<RowXML> makeReportWithUniqueHeaders(RowFunctionBean rowFunctionBean) {
        List<RowXML> rowXMLs = new ArrayList<RowXML>();
        Map<String, RowXML> rowXMLMap = new HashMap<String, RowXML>();
        ArgumentBean uniqueFieldArg = null;
        if (rowFunctionBean.getOperatorBean() != null && rowFunctionBean.getOperatorBean().getArguments() != null && rowFunctionBean.getOperatorBean().getArguments().size() > 0) {
            uniqueFieldArg = rowFunctionBean.getOperatorBean().getArguments().get(0);
        }
        ArgumentBean resolveColumnNum = null;
        if(rowFunctionBean.getOperatorBean() != null && rowFunctionBean.getOperatorBean().getArguments() != null && rowFunctionBean.getOperatorBean().getArguments().size() > 1){
            resolveColumnNum = rowFunctionBean.getOperatorBean().getArguments().get(1);
        }
        ArgumentBean valueColumn = null;
        if(rowFunctionBean.getOperatorBean() != null && rowFunctionBean.getOperatorBean().getArguments() != null && rowFunctionBean.getOperatorBean().getArguments().size() > 2){
            valueColumn = rowFunctionBean.getOperatorBean().getArguments().get(2);
        }
        RowXML headersRow = new RowXML(new ArrayList<String>());
        if(rowFunctionBean.getRowFunctions() != null && rowFunctionBean.getRowFunctions().size() > 0){
            List<RowXML> headerColumns = calculateRowFunctionRows(rowFunctionBean.getRowFunctions().get(0));
            if(headerColumns != null && headerColumns.size() > 0){
                headersRow = headerColumns.get(0);

            }
        }
        rowXMLs.add(headersRow);
        if (uniqueFieldArg != null && resolveColumnNum != null && valueColumn != null) {
            resultSet.beforeFirst();
            int rowNumber = 1;
            logger.debug("Row function == makeReportWithUniqueHeaders: " + rowFunctionBean);
            while (resultSet.next()) {
                String uniqueFieldValue = getArgumentValue(uniqueFieldArg, rowNumber);
                String currentHeaderName = getArgumentValue(resolveColumnNum, rowNumber);
                String columnValue = getArgumentValue(valueColumn, rowNumber);
                RowXML rowXML = null;
                if (uniqueFieldArg.getDefaultValue() == null || uniqueFieldArg.getDefaultValue().equalsIgnoreCase(uniqueFieldValue)) {
                    rowXML = rowXMLMap.get(uniqueFieldValue);
                }
                if (rowXML == null) {
                    rowXML = new RowXML(new ArrayList<String>());
                }
                addColumnBeanValues(rowFunctionBean, rowXML, rowNumber);
                Integer columnNumToSetValue = 0;
                /*if (rowFunctionBean.getColumnBeans() != null) {
                    columnNumToSetValue += rowFunctionBean.getColumnBeans().size();
                }*/
                if(headersRow.getValues() != null && headersRow.getValues().indexOf(currentHeaderName) != -1){
                    columnNumToSetValue += headersRow.getValues().indexOf(currentHeaderName);
                    putValueToRow(rowXML, columnValue, columnNumToSetValue, "0");
                }
                fillSmallRowsByDefault(rowXML, headersRow, "0");
                rowXMLMap.put(uniqueFieldValue, rowXML);
                rowNumber++;
            }
        }
        if(rowXMLMap.values() != null){
            rowXMLs.addAll(rowXMLMap.values());
        }
        return rowXMLs;

    }

    private void fillSmallRowsByDefault(RowXML rowXML, RowXML headersRow, String defaultValue){
        if(rowXML.getValues() == null){
            rowXML.setValues(new ArrayList<String>());
        }
        while(headersRow.getValues().size() > rowXML.getValues().size()){
            rowXML.getValues().add(defaultValue);
        }
    }


}