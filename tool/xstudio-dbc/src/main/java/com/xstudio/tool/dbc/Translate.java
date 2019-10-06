package com.xstudio.tool.dbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaobiao
 * @version 2018/9/11
 */
public class Translate {
    private static Logger logger = LogManager.getLogger(Translate.class);


    private static String REG_VERSION = "^(VERSION)\\s*\"([\\w\\-\\.]*)\"$";
    private static String REG_MESSAGE = "^BO_\\s*(\\d*)\\s*(\\w*)\\:\\s*(\\d*)\\s*(\\w*)$";
    private static String REG_SIGNAL = "^\\s*SG_\\s+([\\w\\_]*)\\s*([\\w\\_]*):\\s+(\\d*)\\|(\\d*)\\@(\\d*)([\\+|\\-])\\s+\\(([\\d\\.\\-]*)\\,([\\d\\.\\-]*)\\)\\s+\\[([\\d\\.\\-]*)\\|([\\d\\.\\-]*)\\]\\s+\"(\\w*)\"\\s+([\\w\\,]*)\\s*$";

    private static Pattern versionPattern = Pattern.compile(REG_VERSION);
    private static Pattern messagePattern = Pattern.compile(REG_MESSAGE);
    private static Pattern signalPattern = Pattern.compile(REG_SIGNAL);

    public static Dbc read(File dbcFile) throws FileNotFoundException {
        Dbc dbc = new Dbc(dbcFile.getName());

        FileReader reader = new FileReader(dbcFile);
        try (BufferedReader br = new BufferedReader(reader)) {
            String str = null;
            HashMap<Integer, DbcMessage> messages = new HashMap<>();
            DbcMessage dbcMessage;
            DbcSignal signal;
            Matcher matcher;
            Boolean gettedVersion = false;
            while ((str = br.readLine()) != null) {
                logger.info(str);
                // 获取DBC版本信息
                if (!gettedVersion) {
                    matcher = versionPattern.matcher(str);
                    if (matcher.find()) {
                        gettedVersion = true;
                        dbc.setVersion(str);
                        continue;
                    }
                }
                // 获取报文信息
                matcher = messagePattern.matcher(str);
                if (matcher.find()) {
                    logger.debug("赋值报文信息");
                    dbcMessage = new DbcMessage();
                    dbcMessage.setMessageId(Integer.valueOf(matcher.group(1)));
                    dbcMessage.setMessageName(matcher.group(2));
                    dbcMessage.setMessageSize(Integer.valueOf(matcher.group(3)));
                    dbcMessage.setTransmitter(matcher.group(4));

                    // 获取message下的信号信息
                    while ((str = br.readLine()) != null) {
                        matcher = signalPattern.matcher(str);
                        if (matcher.find()) {
                            logger.debug("赋值信号信息 {}", dbcMessage.getMessageId());
                            signal = new DbcSignal(matcher);
                            dbcMessage.addSignal(signal);
                            continue;
                        }
                        messages.put(dbcMessage.getMessageId(), dbcMessage);
                        break;
                    }
                }
            }

            reader.close();
            dbc.setMessages(messages);
        } catch (IOException e) {
            logger.error("DBC文件读行异常", e);
        }


        return dbc;
    }
}
