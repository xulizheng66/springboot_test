package com.turbur.commons.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;


/**
 * 将二维码矩阵输出到图片上面
 *
 * 二维码的生成需要借助MatrixToImageWriter类，该类是由Google提供的，可以将该类直接拷贝到源码中使用，当然你也可以自己写个
 * 生产条形码的基类
 *
 */

public class MatrixToImageWriter {

    private static final int BLACK = 0xFF000000;//用于设置图案的颜色
    private static final int WHITE = 0xFFFFFFFF; //用于背景色

    private MatrixToImageWriter() {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y,  (matrix.get(x, y) ? BLACK : WHITE));
//                image.setRGB(x, y,  (matrix.get(x, y) ? Color.YELLOW.getRGB() : Color.CYAN.getRGB()));
            }
        }
        return image;

    }


    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        //设置logo图标
        LogoConfig logoConfig = new LogoConfig();
        image = logoConfig.LogoMatrix(image);

        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }else{
            System.out.println("图片生成成功！");
        }
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        //设置logo图标
//        LogoConfig logoConfig = new LogoConfig();
//        image = logoConfig.LogoMatrix(image);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    public static void crateQR(String ur) throws Exception {
        //String text = "http://172.16.9.212/down"; // 二维码内容
        int width = 400; // 二维码图片宽度
        int height = 400; // 二维码图片高度
        String format = "jpg";// 二维码的图片格式

        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码

        BitMatrix bitMatrix = new MultiFormatWriter().encode(ur,
                BarcodeFormat.QR_CODE, width, height, hints);
        // 生成二维码
//        File outputFile = new File(System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"qr.jpg");
        File outputFile = new File("C:\\Users\\xulz\\Desktop\\" + File.separator + "baidu.jpg");
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        System.out.println("=========");
    }

}
