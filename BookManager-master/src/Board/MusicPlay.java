package Board;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicPlay implements Runnable{

    @Override
    public void run() {
        try
        {
            File musicPath = new File("烟花易冷-Jay Chou.wav");  //在同一目录下，只用写文件名即可
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);//依照路径创建音频输入流
            Clip clip = AudioSystem.getClip();  //创建一个实例化clip对象
            clip.open(audioInput);  //使用Cilp类中的open方法打开音频输入流
            clip.start();  //开始播放音乐
            clip.loop(Clip.LOOP_CONTINUOUSLY); //设置循环方式和线程休眠时间
            Thread.sleep(1000000000);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();  //异常显示在命令行中
        }
    }
}