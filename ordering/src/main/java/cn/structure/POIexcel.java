package cn.structure;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFDrawing;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;

import java.io.FileOutputStream;

public class POIexcel {
    public static void main(String[] args) throws Exception {
//            Thread.sleep(10000);
        String path = "E:\\app\\";
        // 1.创建一个工作簿。03
        SXSSFWorkbook workbook = new SXSSFWorkbook(10); // 07和03版本只有对象不同，其他操作一样
        // 2.创建一个工作表
        SXSSFSheet sheet = workbook.createSheet("统计表");
        SXSSFDrawing p = sheet.createDrawingPatriarch();

        for (int i = 0; i < 100_0000; i++) {
            String s = "我们都一样我们都一样我们都一样我们都一样我们都一样我们都一样我们都一样我们都一样我们都一样我们都一样我们都一样我们都一样";
            String wp = "字段名：你说的话分类算法记录\n方法名：十六客服的哦房间里为了减肥\n规则：345534534535";

            // 3.创建行。第一行
            Row row = sheet.createRow(i);
            // 4.创建列。
            // (1,1) 第一行第一列的单元格
            for (int j = 0; j < 20; j++) {

                Cell cell = row.createCell(j);
                cell.setCellValue(s);
                ClientAnchor anchor = new XSSFClientAnchor();
                anchor.setDx1(0);
                anchor.setDx2(0);
                anchor.setDy1(0);
                anchor.setDy2(0);
                anchor.setCol1(cell.getColumnIndex());
                anchor.setRow1(cell.getRowIndex());
                anchor.setCol2(cell.getColumnIndex() + 6);
                anchor.setRow2(cell.getRowIndex() + 20);
                //这里导致内存溢出
//                Comment comment = p.createCellComment(anchor);
//                comment.setString(new XSSFRichTextString(wp));

//             将批注添加到单元格对象中
//                    cell.setCellComment(comment);
            }
        }
        // 5.生成一张表。03版本的工作簿是以.xls结尾
        FileOutputStream fileOutputStream = new FileOutputStream(path + "03.xls");
        // 输出
        workbook.write(fileOutputStream);
        workbook.dispose();
        // 6.关闭流
        workbook.close();
        fileOutputStream.close();
        System.out.println("03表生成成功！");
    }
}
