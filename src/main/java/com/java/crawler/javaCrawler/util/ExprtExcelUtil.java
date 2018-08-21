package com.java.crawler.javaCrawler.util;


import com.java.crawler.javaCrawler.domain.ExportDto;
import com.java.crawler.javaCrawler.domain.MoveInfoDto;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;

public class ExprtExcelUtil {

    public void writeExcwl(List<ExportDto> dataList, String header){

        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("电影表");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1=sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell=row1.createCell(0);
        //设置单元格内容
        cell.setCellValue(header);
        for (int i=0;i<dataList.size();i++) {
            String name =dataList.get(i).getName() ;
            List<MoveInfoDto> valueList = dataList.get(i).getValueList();
            if(i==0){
                HSSFRow row2=sheet.createRow(i+1);
                HSSFCell cel2=row2.createCell(0);
                cel2.setCellValue(name);
            }else {
                HSSFRow row2=sheet.createRow(i+dataList.get(i-1).getValueList().size()+4);
                HSSFCell cel2=row2.createCell(0);
                cel2.setCellValue(name);
            }
            for (int j=0;j<valueList.size();j++){
                //在sheet里创建第二行
                HSSFRow row3=null;
                if(i==0){
                    row3 =sheet.createRow(j+2);
                }else{
                    row3 =sheet.createRow(dataList.get(i-1).getValueList().size()+j+7);
                }

                //创建单元格并设置单元格内容
                row3.createCell(0).setCellValue(valueList.get(j).getId());
                row3.createCell(1).setCellValue(valueList.get(j).getMovieId());
                row3.createCell(2).setCellValue(valueList.get(j).getMovieName());
                row3.createCell(3).setCellValue(valueList.get(j).getCompany());
                row3.createCell(4).setCellValue(valueList.get(j).getArtName());
                row3.createCell(5).setCellValue(valueList.get(j).getResulet());
                row3.createCell(6).setCellValue(valueList.get(j).getAttrbute());
                row3.createCell(7).setCellValue(valueList.get(j).getDescrip());
            }
        }

        try {
           String fileName = "E:/电影表单.xls";
            FileOutputStream fout = new FileOutputStream(fileName);
            wb.write(fout);
            fout.close();
        } catch (Exception e)  {
            e.printStackTrace();
        }


    }
}
