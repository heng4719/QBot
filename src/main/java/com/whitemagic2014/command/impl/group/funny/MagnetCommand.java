package com.whitemagic2014.command.impl.group.funny;

import com.whitemagic2014.command.EverywhereCommand;
import com.whitemagic2014.command.impl.everywhere.BaseEveryWhereCommand;
import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class MagnetCommand implements EverywhereCommand {
    List<String> lines;
    private int totalNum;

    public MagnetCommand() throws IOException {
        //读取种子文件
        /**
         * 种子文件格式为
         * 视频名称
         * 视频下载链接
         * 空行
         *
         * 最后一个不用空行
         */
        try{
            Path path = Paths.get("text");
            lines = Files.readAllLines(path);
            totalNum = (lines.size() + 1)/3;
            System.out.println("totalNum: " + totalNum);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("种子", "链接");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) throws Exception {
        //随机数
        int max=totalNum,min=1;
        int ran = (int)(min+Math.random()*(max-min+1)) - 1;
        String title = lines.get(ran*3-3);
//        title = getIsoToUtf_8(title);
        String content = lines.get(ran*3-2);
        System.out.println("totalNum: " + totalNum + "ran: " + ran + "title: " + title + "content: " + content);

        return new PlainText(title + ": " + content);

//        At at = new At(sender);
//        PlainText plainText = new PlainText(title + ": " + content);
//        return at.plus(plainText);
    }
}
