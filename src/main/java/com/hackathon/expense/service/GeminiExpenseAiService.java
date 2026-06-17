package com.hackathon.expense.service;

import com.hackathon.expense.dto.AiAnalysisResult;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GeminiExpenseAiService {

    private static final Logger log = LoggerFactory.getLogger(GeminiExpenseAiService.class);

    /**
     * 模擬呼叫 Gemini 等大模型 API：依品項名稱與金額生成分類與幽默省錢建議。
     * 黑客松現場可將此方法改為真實 HTTP 呼叫。
     */
    public AiAnalysisResult analyzeExpense(String title, BigDecimal amount) {
        log.info("Simulating Gemini API call for title='{}', amount={}", title, amount);

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String normalizedTitle = title == null ? "" : title.trim().toLowerCase();
        String category = detectCategory(normalizedTitle);
        String advice = generateHumorousAdvice(category, title, amount);

        log.info("Gemini mock response -> category={}, advice={}", category, advice);
        return new AiAnalysisResult(category, advice);
    }

    private String detectCategory(String title) {
        if (containsAny(title, "咖啡", "早餐", "午餐", "晚餐", "便當", "飲料", "奶茶", "火鍋", "拉麵", "餐", "吃")) {
            return "飲食";
        }
        if (containsAny(title, "捷運", "公車", "計程車", "uber", "高鐵", "火車", "油錢", "停車", "交通")) {
            return "交通";
        }
        if (containsAny(title, "電影", "遊戲", "ktv", "演唱會", "netflix", "spotify", "娛樂")) {
            return "娛樂";
        }
        if (containsAny(title, "衣服", "鞋子", "3c", "手機", "電腦", "amazon", "蝦皮", "購物")) {
            return "購物";
        }
        if (containsAny(title, "房租", "水電", "瓦斯", "網路", "保險", "醫療", "藥")) {
            return "生活";
        }
        return "其他";
    }

    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private String generateHumorousAdvice(String category, String title, BigDecimal amount) {
        String safeTitle = title == null || title.isBlank() ? "這筆神祕消費" : title;
        BigDecimal safeAmount = amount == null ? BigDecimal.ZERO : amount;

        return switch (category) {
            case "飲食" -> String.format(
                    "「%s」花了 %s 元？下次先問胃：你是餓了，還是只是無聊。自帶水壺，錢包會謝謝你。",
                    safeTitle, safeAmount);
            case "交通" -> String.format(
                    "「%s」%s 元已出站。建議多走路，順便當健身；錢省了，腿也省了健身房會費。",
                    safeTitle, safeAmount);
            case "娛樂" -> String.format(
                    "「%s」娛樂支出 %s 元。提醒：快樂可以打折，但帳單不會。先睡一覺再決定要不要買。",
                    safeTitle, safeAmount);
            case "購物" -> String.format(
                    "「%s」%s 元？購物車裡的東西不會過期，但你的存款會。把商品放 24 小時，通常就會自己消失。",
                    safeTitle, safeAmount);
            case "生活" -> String.format(
                    "「%s」生活必要開銷 %s 元。該花的還是要花，但記得順手關燈，省下的電費也是薪水。",
                    safeTitle, safeAmount);
            default -> String.format(
                    "「%s」%s 元落入「其他」黑洞。建議記帳時多寫兩個字，不然月底會以為錢被貓咪吃掉了。",
                    safeTitle, safeAmount);
        };
    }
}
