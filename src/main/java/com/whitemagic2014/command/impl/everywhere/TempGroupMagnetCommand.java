package com.whitemagic2014.command.impl.everywhere;

import com.whitemagic2014.command.TempMessageCommand;
import com.whitemagic2014.pojo.CommandProperties;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class TempGroupMagnetCommand implements TempMessageCommand {
    List<String> lines;
    private int totalNum;

    public TempGroupMagnetCommand() throws IOException {
        //读取种子文件
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
    public Message execute(Member sender, ArrayList<String> args, MessageChain messageChain, Member subject) throws Exception {
        int max=totalNum,min=1;
        int ran = (int)(min+Math.random()*(max-min+1)) - 1;
        String title = lines.get(ran*3-3);
        String content = lines.get(ran*3-2);
        System.out.println("totalNum: " + totalNum + "ran: " + ran + "title: " + title + "content: " + content);

        return new PlainText(title + ": " + content);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("种子", "链接");
    }
}
