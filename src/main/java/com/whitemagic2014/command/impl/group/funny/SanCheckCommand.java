package com.whitemagic2014.command.impl.group.funny;

import com.whitemagic2014.command.impl.group.NoAuthCommand;
import com.whitemagic2014.dao.MemberSanDao;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.MemberSan;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component
public class SanCheckCommand extends NoAuthCommand {

    @Autowired
    MemberSanDao msd;

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
//        if (!args.isEmpty() && args.size() != 1) {
            boolean flag = false;
            boolean checkFlag = false;
            //默认检测为 0/(1D5)
            //先判断数据库中是否存在该用户数据
            String uid = String.valueOf(sender.getId());
            System.out.println("qid: " + uid);
            MemberSan memberSan = msd.findMemberSan(uid);
            if(memberSan == null){
                //新用户，创建记录
                memberSan = new MemberSan();
                memberSan.setUid(uid);
                memberSan.setSan(50);
                msd.insertMemberSan(memberSan);
                flag = true;
            }
            //如果是新增了的用户，那么重新查询一次，为了能够查询到该记录的id，便于更新
            if(flag){
                memberSan = msd.findMemberSan(uid);
            }
            //开始sancheck
            Random random = new Random();
            int failRandNum = 0;
            int randNum = (random.nextInt(100) + 1);
            if(randNum > memberSan.getSan()){
                //检定失败，进行失败检定
                checkFlag = true;
                failRandNum = (random.nextInt(5) + 1);
            }
            //更新san值
            memberSan.setSan(memberSan.getSan() - failRandNum);
            msd.updateMemberSan(memberSan);
            String result = "SanCheck(0/1D5)结果：\n  1D100 -> " + randNum;
            if(!checkFlag){
                //检定成功
                result += "\n 检定成功";
            }else {
                //鉴定失败
                result += "\n 检定失败，1D5 -> " + failRandNum + "\n 失去理智" + failRandNum + "点";
            }
            result += "\n--------\n当前理智：" + memberSan.getSan() + "点";

        At at = new At(sender);
        PlainText plainText = new PlainText(result);
        System.out.println("args: " + args.toString());
        System.out.println("sender: " + sender.toString());
        System.out.println("messageChain: " + messageChain.toString());
        System.out.println("subject: " + subject.toString());

        return at.plus(plainText);
//        }else{
//            System.out.println("args: " + args.toString());
//        }

    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("sancheck", "sc", "SanCheck");
    }
}
