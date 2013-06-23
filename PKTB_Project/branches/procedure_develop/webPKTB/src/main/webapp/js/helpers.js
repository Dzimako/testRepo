var log = typeof console != 'undefined'
				? function (x) { console.log(x) } 
				: function () {},
		dir = typeof console != 'undefined' 
				? function (x) { console.dir(x) } 
				: function () {}

 pktbConfirm = function(text, ok, cancel, title) {
  var dialog = $('<div>', { text: text })
	  				 .addClass('pktb')
	  				 .appendTo(document.body);
      
  dialog.dialog({
   	title: title,
    resizable: false,
    modal: true,
    buttons: {
    	'ОК': function() {
       	dialog.remove();
       	if (ok) ok();
       },
       'Отмена': function() {
         	dialog.remove();
             if (cancel) cancel();
    	}
  	}
	}).css('min-height', 0);
},
pktbAlert = function(text, title) {
	var dialog = $('<div>', { text: text })
						 .addClass('pktb')
						 .appendTo(document.body);
	dialog.dialog({
  	title: title,
    resizable: false,
		modal: true,
    buttons: {
    	'ОК': function() { 
    		dialog.remove(); 
    	}
    }
	}).css('min-height', 0);
}


var jsonTest = {
  "reportId": 674,
  "userBean": {
    "userId": 3
  },
  "datasource": "jdbc/pktb_spb_cod_test",
  "proc_name": "spv2982",
  "sqlStatement": "",
  "pairList": [
    {
      "name": "period",
      "value": "2",
      "orderNum": "17",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "kodAdm",
      "value": "20",
      "orderNum": "11",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "1111",
      "orderNum": "1",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "*",
      "orderNum": "2",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "rwCode",
      "value": "01",
      "orderNum": "3",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "**",
      "orderNum": "4",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "*",
      "orderNum": "5",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "******",
      "orderNum": "6",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "*",
      "orderNum": "7",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "*",
      "orderNum": "8",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "*",
      "orderNum": "9",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "*",
      "orderNum": "10",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "*",
      "orderNum": "12",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "*",
      "orderNum": "13",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "01",
      "orderNum": "14",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "06",
      "orderNum": "15",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "",
      "value": "07",
      "orderNum": "16",
      "inputParam": "in",
      "paramDataType": "12"
    },
    {
      "name": "P1",
      "value": "",
      "orderNum": "18",
      "inputParam": "out",
      "paramDataType": "5"
    },
    {
      "name": "P2",
      "value": "",
      "orderNum": "19",
      "inputParam": "out",
      "paramDataType": "4"
    }
  ],
  "tableBeans": [
    {
      "inputResultSet": {
        "name": "spv2982"
      },
      "outputResultSet": {
        "name": "outputTableResult1"
      },
      "columns": [

      ],
      "rowFunctions": [

      ]
    },
    {
      "inputResultSet": {
        "name": "spv2982"
      },
      "outputResultSet": {
        "name": "outputTableResult"
      },
      "columns": [

      ],
      "rowFunctions": [
        {
          "operator": {
            "operatorKey": "UNIQUE_HEADERS_REPORT",
            "arguments": [
              {
                "columnNumber": "5"
              },
              {
                "columnNumber": "2"
              },
              {
                "columnNumber": "7"
              }
            ]
          },
          "columns": [
            {
              "operator": {
                "operatorKey": "column",
                "arguments": [
                  {
                    "columnNumber": "5"
                  }
                ]
              }
            },
            {
              "operator": {
                "operatorKey": "column",
                "arguments": [
                  {
                    "defaultValue": "всего_строчное"
                  }
                ]
              }
            }
          ],
          "rowFunctions": [
            {
              "operator": {
                "operatorKey": "DYNAMIC_HEADERS",
                "arguments": [
                  {
                    "columnNumber": "2"
                  }
                ]
              },
              "columns": [
                {
                  "operator": {
                    "operatorKey": "column",
                    "arguments": [
                      {
                        "defaultValue": "По дороге"
                      }
                    ]
                  }
                },
                {
                  "operator": {
                    "operatorKey": "column",
                    "arguments": [
                      {
                        "defaultValue": "Вагонов"
                      }
                    ]
                  }
                }
              ],
              "rowFunctions": [

              ],
              "resultSetToUse": {
                "name": ""
              },
              "repeatColumns": [

              ]
            }
          ],
          "resultSetToUse": {
            "name": ""
          },
          "repeatColumns": [

          ]
        },
        {
          "operator": {
            "operatorKey": "TOTAL_BY_UNIQUE"
          },
          "columns": [
            {
              "operator": {
                "operatorKey": "dictionary",
                "arguments": [
                  {
                    "defaultValue": "mnemDorNsi"
                  },
                  {
                    "columnNumber": "1"
                  }
                ]
              }
            },
            {
              "operator": {
                "columnRuleBean": {
                  "formulaBean": {
                    "fixedPosition": "true",
                    "singleValue": "false",
                    "numToBegin": "3",
                    "formulaString": "n+1"
                  },
                  "certainColumns": [

                  ]
                },
                "operatorKey": "+"
              }
            }
          ],
          "rowFunctions": [

          ],
          "resultSetToUse": {
            "name": ""
          },
          "repeatColumns": [
            {
              "columnIndexesRule": {
                "formulaBean": {
                  "numToBegin": "3",
                  "formulaString": "n+1"
                },
                "certainColumns": [

                ]
              },
              "column": {
                "operator": {
                  "columnRuleBean": {
                    "formulaBean": {
                      "fixedPosition": "false",
                      "singleValue": "true",
                      "numToBegin": "3",
                      "formulaString": "n"
                    },
                    "certainColumns": [

                    ]
                  },
                  "operatorKey": "column"
                }
              }
            }
          ]
        }
      ]
    }
  ],
  "dictionaries": [
    {
      "dataSource": "jdbc/pktb_spb_cod_test",
      "filterDBkey": "KOD_DORN",
      "filterDBkeyValue": "MNEM_DOR",
      "databaseTable": "TN_DOR_COD",
      "dictionaryKey": "mnemDorNsi",
      "exceptions": [

      ]
    },
    {
      "dataSource": "jdbc/pktb_spb_cod_test",
      "filterDBkey": "KOD_DORN",
      "filterDBkeyValue": "KOD_ADM",
      "databaseTable": "TN_DOR_COD",
      "dictionaryKey": "dor2AdmNsi",
      "exceptions": [

      ]
    },
    {
      "dataSource": "jdbc/pktb_spb_cod_test",
      "filterDBkey": "KOD_ADM",
      "filterDBkeyValue": "MNEM_ADM",
      "databaseTable": "TN_DOR_COD",
      "dictionaryKey": "mnemAdmNsi",
      "exceptions": [
        {
          "key": "00",
          "value": "Все"
        },
        {
          "key": "99",
          "value": "HEPЗД"
        }
      ]
    }
  ],
  "importedDictionaries": [
    "mnemAdmNsi",
    "dor2AdmNsi",
    "mnemDorNsi"
  ],
  "resultSets": [
    {
      "key": "spv2982",
      "datasource": "jdbc/pktb_spb_cod_test",
      "sqlStatement": "",
      "proc_name": "spv2982",
      "params": [

      ]
    }
  ]
}