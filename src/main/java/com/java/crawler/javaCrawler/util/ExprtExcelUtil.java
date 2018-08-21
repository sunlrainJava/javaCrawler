package com.java.crawler.javaCrawler.util;


import com.java.crawler.javaCrawler.domain.ExportDto;
import com.java.crawler.javaCrawler.domain.MoveInfoDto;
import org.apache.poi.hssf.usermodel.*;

import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExprtExcelUtil {

    public void writeExcwl(List<ExportDto> dataList, String header,String path){

        int blanknum =4 ;
        //ExecutorService executorService = new ThreadPoolExecutor();
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setWrapText(true);
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("电影表");
        //setBorder.setWrapText(true);
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1=sheet.createRow(0);
        sheet.setColumnWidth((short) 7, (short) 3000);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell=row1.createCell(0);
        //设置单元格内容
        cell.setCellValue(header);

        HSSFRow row2=sheet.createRow(1);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        row2.createCell(0).setCellValue("序号");
        row2.createCell(1).setCellValue("备案立项号");
        row2.createCell(2).setCellValue("备案单位");
        row2.createCell(3).setCellValue("编剧");
        row2.createCell(4).setCellValue("备案结果");
        row2.createCell(5).setCellValue("备案地");
        row2.createCell(6).setCellValue("梗概");

        for (int i=0;i<dataList.size();i++) {
            String name =dataList.get(i).getName() ;
            List<MoveInfoDto> valueList = dataList.get(i).getValueList();
            int sum =0;
            for(int n=i;n>0;n--){
                int num = dataList.get(n-1).getValueList().size()+2;
                sum += num;
            }

            /*if(i==0){
                HSSFRow fenleiRow=sheet.createRow(i+3);
                HSSFCell fenleiCel=fenleiRow.createCell(0);
                fenleiCel.setCellValue(name);
            }else {*/
                HSSFRow fenleiRow=sheet.createRow(i+sum+blanknum-1);
                HSSFCell fenleiCel=fenleiRow.createCell(0);
                fenleiCel.setCellValue(name);
            //}
            for (int j=0;j<valueList.size();j++){
                //在sheet里创建第二行
                HSSFRow dataRow=null;
                /*if(i==0){
                    dataRow =sheet.createRow(j+blanknum+1);
                }else{*/
                    dataRow =sheet.createRow(i+sum+j+blanknum);
               // }

                //创建单元格并设置单元格内容
                dataRow.createCell(0).setCellValue(valueList.get(j).getId());
                dataRow.createCell(1).setCellValue(valueList.get(j).getMovieId());
                dataRow.createCell(2).setCellValue(valueList.get(j).getMovieName());
                dataRow.createCell(3).setCellValue(valueList.get(j).getCompany());
                dataRow.createCell(4).setCellValue(valueList.get(j).getArtName());
                dataRow.createCell(5).setCellValue(valueList.get(j).getResulet());
                dataRow.createCell(6).setCellValue(valueList.get(j).getAttrbute());
                dataRow.createCell(7).setCellValue(valueList.get(j).getDescrip());
            }
        }

        try {
           String fileName = path+header+".xls";
            FileOutputStream fout = new FileOutputStream(fileName);
            wb.write(fout);
            fout.close();
        } catch (Exception e)  {
            e.printStackTrace();
        }


    }
}
