/**
 * 
 */
package appDomain;

import java.io.*;
import java.util.*;

import implementations.MyQueue;
import implementations.MyStack;

class TagWithLine {
    String tag;
    int lineNumber;

    TagWithLine(String tag, int lineNumber) {
        this.tag = tag;
        this.lineNumber = lineNumber;
    }
}

public class OurXMLParser {

    public static void main(String[] args) {
        // 输入 XML 文件路径
        String filePath = "res/sample2.xml"; // 替换为实际路径

        try {
            List<String> lines = readFile(filePath);
            MyQueue<String> errorQ = new MyQueue<>(); // 错误队列
            MyQueue<String> extrasQ = new MyQueue<>(); // 额外标签队列
            MyStack<String> stack = new MyStack<>(); // 栈用于存储开始标签

            // 逐行解析 XML 文件
            for (String line : lines) {
                String[] tokens = tokenizeXML(line);

                for (String token : tokens) {
                	if (isTagMalformed(token)) {
                        // 检测到标签不规范
                        errorQ.enqueue("Malformed tag: " + token);
                        continue; // 跳过处理此标签
                    }
                    if (isSelfClosingTag(token)) {
                        // 忽略自闭合标签
                        continue;
                    } else if (isStartTag(token)) {
                        // 遇到开始标签，压入栈
                        stack.push(token);
                    } else if (isEndTag(token)) {
                        // 处理结束标签
                        if (!stack.isEmpty() && matches(stack.peek(), token)) {
                            stack.pop(); // 匹配成功，弹出栈顶元素
                        } else if (!errorQ.isEmpty() && matches(errorQ.peek(), token)) {
                            errorQ.dequeue(); // 匹配错误队列的头部元素
                        } else if (stack.isEmpty()) {
                            errorQ.enqueue(token); // 栈为空，加入错误队列
                        } else {
                            // 栈中没有匹配，搜索匹配标签
                            int matchIndex = findMatchingStartTag(stack, token);
                            if (matchIndex != -1) {
                                // 栈中有匹配标签，弹出直到匹配
                                while (stack.size() > matchIndex) {
                                    errorQ.enqueue(stack.pop());
                                }
                            } else {
                                extrasQ.enqueue(token); // 加入额外标签队列
                            }
                        }
                    }
                }
            }

            // 处理栈中剩余的标签
            while (!stack.isEmpty()) {
                errorQ.enqueue(stack.pop());
            }

            // 处理错误队列和额外标签队列
            processQueues(errorQ, extrasQ);

            System.out.println("Parsing complete.");
        } catch (Exception e) {
            System.err.println("Error reading or parsing the XML file: " + e.getMessage());
        }
    }

    // 读取文件内容
    private static List<String> readFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
            	if (lineCount < 1) {
                    lineCount++;
                    continue;  // 跳过前1行
                }
                lines.add(line.trim());
            }
        }
        return lines;
    }

    // 将 XML 文本分解为标签
    private static String[] tokenizeXML(String line) {
    	return line.split("(?<=>)(?=<)|(?=<)(?=>)");
    }
    
    private static boolean isTagMalformed(String token) {
        // 检查是否有多个连续的 '<' 或 '>'
        return token.contains(">>") || token.contains("<<");
    }

    // 判断是否为自闭合标签
    private static boolean isSelfClosingTag(String token) {
    	return token.matches("<[a-zA-Z][^<>]*/>");
    }

    // 判断是否为开始标签
    private static boolean isStartTag(String token) {
    	return token.matches("<[^/][^<>]+>") && token.indexOf(">") == token.lastIndexOf(">");
    }

    // 判断是否为结束标签
    private static boolean isEndTag(String token) {
    	return token.matches("</[^<>]+>") && token.indexOf(">") == token.lastIndexOf(">");
    }

    // 检查标签是否匹配
    private static boolean matches(String startTag, String endTag) {
    	// 去掉开始标签的 < 和结束标签的 </，再比较标签名
        String startTagName = startTag.replaceAll("<(/?)([^>]+)>", "$2").split("\\s+")[0]; // 获取标签名
        String endTagName = endTag.replaceAll("</([^>]+)>", "$1"); // 获取结束标签名

        return startTagName.equals(endTagName);
    }

    // 在栈中寻找匹配的开始标签
    private static int findMatchingStartTag(MyStack<String> stack, String endTag) {
        // 将结束标签转换为开始标签格式
        String startTag = endTag.replace("</", "<");

        // 提取标签名，找到第一个空格之前的部分
        int spaceIndex = startTag.indexOf(" ");
        if (spaceIndex != -1) {
            startTag = startTag.substring(0, spaceIndex); // 取第一个空格前的部分
        }

        // 使用 search 方法查找元素在栈中的位置
        int positionFromTop = stack.search(startTag);

        // 如果未找到元素，返回 -1
        return positionFromTop;
    }





    private static void processQueues(MyQueue<String> errorQ, MyQueue<String> extrasQ) {
        while (!errorQ.isEmpty() || !extrasQ.isEmpty()) {
            if (errorQ.isEmpty()) {
                // 如果错误队列为空，处理剩余的额外标签
                System.out.println("Unmatched extra tags:");
                printErrors(extrasQ);
                break;
            }
            if (extrasQ.isEmpty()) {
                // 如果额外标签队列为空，处理剩余的错误标签
                System.out.println("Unmatched error tags:");
                printErrors(errorQ);
                break;
            }

            // 提取错误队列和额外队列的头部标签
            String error = errorQ.peek();
            String extra = extrasQ.peek();

            // 提取标签名，忽略属性
            String errorName = error.replaceAll("<(/?)([^\\s>]+).*?>", "$2");
            String extraName = extra.replaceAll("<(/?)([^\\s>]+).*?>", "$2");

            if (errorName.equals(extraName)) {
                // 如果标签名匹配，移除两个队列的头部元素
                //System.out.println("Resolved mismatch: " + error + " matches " + extra);
                errorQ.dequeue();
                extrasQ.dequeue();
            } else {
                // 如果不匹配，记录错误并从错误队列移除头部标签
                System.out.println("Error: Unmatched tag - " + error);
                errorQ.dequeue();
            }
        }
    }


    // 打印错误信息
    private static void printErrors(MyQueue<String> errors) {
        while (!errors.isEmpty()) {
            System.out.println("Error: " + errors.dequeue());
        }
    }
}

