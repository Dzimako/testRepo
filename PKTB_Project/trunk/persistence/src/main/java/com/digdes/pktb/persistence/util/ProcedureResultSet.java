package com.digdes.pktb.persistence.util;

import java.util.List;

/**
 * User: Kozlov.D
 * Date: 22.11.12
 * Time: 12:57
 */
public class ProcedureResultSet {
    private List<String> outParamValues;
    private List<String> columnNames;
    private List<List<String>> resultSetData;
    private Integer currentRowNum;

    public void setOutParamValues(List<String> outParamValues) {
        this.outParamValues = outParamValues;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public void setResultSetData(List<List<String>> resultSetData) {
        this.resultSetData = resultSetData;
    }

    public List<String> getOutParamValues() {
        return outParamValues;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    /*
    * Метод прокручивает ResultSet на одну строку вперед.
    * Возвращает true если прокрутка осуществилась успешно*/
    public Boolean next(){
        if(resultSetData != null && resultSetData.size() > 0){      //если записана хотя бы одна строка данных
            if(currentRowNum == null){                                  //если номер текущей строки не задан
                currentRowNum = 0;                                      //присваиваем номер начальной строки
                return true;
            } else {                                                    //если номер текущей строки был ранее задан
                currentRowNum++;                                        //инкрементируем номер текущей строки
                return currentRowNum < resultSetData.size();            //если номер строки не вышел за пределы записанных, то успешно, в противном случае -- не успешно
            }
        } else {                                                    //если нет записанных данных
            return false;                                           //прокрутка невозможна
        }
    }

    /*
    * Метод возвращает значение из текущей строки и столбца с указанным номером*/
    public String getColumnValue(Integer columnNum){
        if(currentRowNum != null && currentRowNum < resultSetData.size()){           //если номер текущей строки уже задан и если он не выходит за пределы уже записанных
            List<String> rsRow = resultSetData.get(currentRowNum);                   //вытаскиваем текущую строку
            if(columnNum < rsRow.size()){                                            //смотрим не выходит ли указанный номер столбца за пределы допустимого кол-ва
                return rsRow.get(columnNum-1);                                         //возвращаем требуемое значение
            } else {
                //TODO кинуть ошибку о том что столбец выходит за рамки таблицы
            }
        } else {
            //TODO кинуть ошибку о том что resultSet закрыт
        }
        return null;
    }

    /*
    * Метод переносит номер текущей строки на первую строку*/
    public Boolean first(){
        if(resultSetData != null && resultSetData.size() > 0){
            currentRowNum = 0;
            return true;
        } else {
            return false;
        }
    }

    /*
    * Метод переносит номер текущей строки "перед" первым.
    * */
    public Boolean beforeFirst(){
        currentRowNum = null;
        return true;
    }

    /*
    * Метод возвращает поличество столбцов в каждой из строк ResultSet*/
    public Integer getNumOfColumns(){
        if(columnNames != null){                                        //если записаны названия столбцов
            return columnNames.size();                                  //возвращаем количество названий(= кол-во столбцов)
        } else {                                                        //если названия столбцов не записаны
            if(resultSetData != null && resultSetData.size() > 0){      //смотрим записана ли хотя бы одна строка данных
                return resultSetData.get(0).size();                     //вытаскиваем количество записей из первой строки(= кол-во столбцов)
            }
        }
        return 0;                                                       //если нет ни названий столбцов, ни данных, то кол-во столбцов == 0
    }
}
