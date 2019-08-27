package com.nowcoder.service;

import com.nowcoder.controller.HomeController;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    @Override
    /**
     * 初始化 ,读取关键词文本
     */
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                addWord(lineTxt);
            }
            System.out.println("读取成功");
            reader.close();
        } catch (Exception e) {
            logger.error("读取敏感词失败" + e.getMessage());
        }
    }

    /**
     * 构建前缀树
     * 给前缀树中添加敏感词
     *
     * @param lineText
     */
    private void addWord(String lineText) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < lineText.length(); i++) {
            Character c = lineText.charAt(i);
            if (isSymbol(c)) {
                continue;
            }
            TrieNode node = tempNode.getSubNode(c);
            if (node == null) {  //没有当前字符节点,新建
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }
            tempNode = node;
//将最后的节点标记为false
            if (i == lineText.length() - 1) {
                tempNode.setKeyWordEnd(true);
            }

        }
    }

    private class TrieNode {

        //关键词的结尾
        private boolean end = false;
        //当前节点下的所有节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        boolean isKeyWordEnd() {
            return end;
        }

        void setKeyWordEnd(boolean end) {
            this.end = end;
        }
    }

    private TrieNode rootNode = new TrieNode();

    /**
     * 过滤非英文/中文的字符
     *
     * @param c
     * @return
     */
    public boolean isSymbol(char c) {
        int ic = (int) c;
        //东亚文字(0x2E80  - 0x9FFF)
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    public String filter(String text) {

        StringBuilder result = new StringBuilder();
        if (StringUtils.isBlank(text)) {
            return text;
        }
        String replacement = "**";
        TrieNode tempNode = rootNode;
        int begin = 0;
        int positon = 0;
        while (positon < text.length()) {
            char c = text.charAt(positon);
            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    result.append(c);
                }
                ++positon;
                continue;
            }
            tempNode = tempNode.getSubNode(c);

            //如果字符不在前缀树中
            if (tempNode == null) {
                result.append(text.charAt((begin)));
                //position begin +1
                positon = begin + 1;
                begin = positon;
                tempNode = rootNode;

            } else if (tempNode.isKeyWordEnd()) {
                //发现敏感词
                //打码
                result.append(replacement);
                positon = positon + 1;
                begin = positon;
            } else {
                // positon继续往后走
                ++positon;
            }

        }
        result.append(text.substring(begin));
        return result.toString();
    }


//    public static void main(String[] args) {
//        SensitiveService s = new SensitiveService();
//
//        System.out.println(s.filter("你色情"));
//    }
}
