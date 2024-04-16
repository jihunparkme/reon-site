package com.site.reon.global.common.util.mail.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MailTemplate {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String generateAuthCodeTemplate(String purpose, String authCode) {
        StringBuffer sb = new StringBuffer();
        sb.append("                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"\n" +
                "                                    style=\"width:100%;max-width:630px;margin-left:auto;margin-right:auto;letter-spacing:-1px\">\n" +
                "                                    <tbody>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"font-size:44px;line-height:48px;font-weight:bold;color:#000000;padding-bottom:60px;text-align:left;letter-spacing:-1px;font-family:나눔고딕,NanumGothic,맑은고딕,Malgun Gothic,돋움,Dotum,Helvetica,Apple SD Gothic Neo,Sans-serif\">\n" +
                "                                                <span>이메일 <span class=\"il\">인증</span><span class=\"il\">번호</span> 안내</span>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td\n" +
                "                                                style=\"padding-bottom:50px;font-size:14px;line-height:22px;font-weight:normal;color:#000000;text-align:left;letter-spacing:-1px;font-family:나눔고딕,NanumGothic,맑은고딕,Malgun Gothic,돋움,Dotum,Helvetica,Apple SD Gothic Neo,Sans-serif\">\n" +
                "                                                <p style=\"margin:0;padding:0\">본 메일은 REONAi 사이트의 ");
        sb.append(purpose);
        sb.append("을 위한 이메일 <span class=\"il\">인증</span>입니다.</p>\n" +
                "                                                <p style=\"margin:0;padding:0\">아래의 [이메일 <span class=\"il\">인증</span><span class=\"il\">번호</span>]를 입력하여 본인확인을 해주시기 바랍니다.</p>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"padding-bottom:50px\">\n" +
                "                                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                    align=\"center\" style=\"width:100%\">\n" +
                "                                                    <tbody>\n" +
                "                                                        <tr>\n" +
                "                                                            <td style=\"padding:50px 30px;border:1px solid #eeeeee;background:#fbfbfb;text-align:center\">\n" +
                "                                                                <p style=\"margin:0;padding:0;font-size:18px;font-weight:bold;color:#000000;letter-spacing:-1px;font-family:나눔고딕,NanumGothic,맑은고딕,Malgun Gothic,돋움,Dotum,Helvetica,Apple SD Gothic Neo,Sans-serif\">");
        sb.append(authCode);
        sb.append(" </p>\n" +
                "                                                            </td>\n" +
                "                                                        </tr>\n" +
                "                                                    </tbody>\n" +
                "                                                </table>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"font-size:14px;line-height:22px;font-weight:normal;color:#000000;text-align:left;letter-spacing:-1px;font-family:나눔고딕,NanumGothic,맑은고딕,Malgun Gothic,돋움,Dotum,Helvetica,Apple SD Gothic Neo,Sans-serif\">\n" +
                "                                                <p style=\"margin:0;padding:0\">감사합니다.<br>REONAi</p>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                            </td>\n");


        return mergeTemplates(sb.toString());
    }

    public static String generateContents(String title, Map<String, String> contentsMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"\n" +
                "                                    style=\"width:100%;max-width:630px;margin-left:auto;margin-right:auto;letter-spacing:-1px\">\n" +
                "                                    <tbody>\n" +
                "                                        <tr>\n" +
                "                                            <td\n" +
                "                                                style=\"font-size:44px;line-height:48px;font-weight:bold;color:#000000;padding-bottom:60px;text-align:left;letter-spacing:-1px;font-family:나눔고딕,NanumGothic,맑은고딕,Malgun Gothic,돋움,Dotum,Helvetica,Apple SD Gothic Neo,Sans-serif\">\n" +
                "                                                <span>");
        sb.append(title);

        sb.append("</span>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <table bgcolor='#eeeeee' cellpadding='10' cellspacing='0' border='0' width='100%' align='center'\n" +
                "                                                style='max-width: 500px; font-family: arial; font-size: 13px; color: #555555;'>\n" +
                "                                                <tr>\n" +
                "                                                    <td>\n" +
                "                                                        <table width='100%' cellpadding='10' cellspacing='2' bgcolor='#eeeeee' border='0' align='left'>");

        for (String key : contentsMap.keySet()) {
            sb.append("<tr width='100%' bgcolor='#ffffff'>\n" +
                    "                                                                <th width='25%'>");
            sb.append(key);
            sb.append("</th>\n" +
                    "                                                                <td width='75%'>");
            sb.append(contentsMap.get(key));
            sb.append("</td>\n" +
                    "                                                            </tr>");
        }
        sb.append("<tr width='100%' bgcolor='#ffffff'>\n" +
                "                                                                <th width='25%'>발송시간 </th>\n" +
                "                                                                <td width='75%'>");
        sb.append(LocalDateTime.now().format(formatter));
        sb.append("</td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                        </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                            </td>");

        return mergeTemplates(sb.toString());
    }

    private static String getMailTemplatePrefix() {
        StringBuffer sb = new StringBuffer();
        sb.append("<table width=\"100%\" style=\"padding:60px 0 60px 0;color:#555;font-size:16px;word-break:keep-all\">\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td>\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"\n" +
                "                    style=\"padding:0px 0px 0px 0px;width:100%;max-width:800px;margin:0 auto;background:#fff\">\n" +
                "                    <tbody>\n" +
                "                        <tr>\n" +
                "                            <td style=\"padding-bottom:22px\">\n" +
                "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"width:100%\">\n" +
                "                                    <tbody>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"width:100%;text-align:left\" valign=\"bottom\">\n" +
                "                                                <a href=\"http://www.reonaicoffee.com/\" target=\"_blank\"\n" +
                "                                                    data-saferedirecturl=\"http://www.google.com/url?q=https://www.reonaicoffee.com\">\n" +
                "                                                    <img src=\"http://www.reonaicoffee.com/img/logo/reonai-logo.png\" alt=\"REONAi\"\n" +
                "                                                        style=\"width: 180px;\">\n" +
                "                                                </a>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "\n" +
                "                        <tr>\n" +
                "                            <td\n" +
                "                                style=\"border:10px solid #ffd400;padding:90px 14px 90px 14px;margin-left:auto;margin-right:auto\">");

        return sb.toString();
    }

    private static String getMailTemplatePostFix() {
        StringBuffer sb = new StringBuffer();
        sb.append("                     </tr>\n" +
                "                    </tbody>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>");

        return sb.toString();
    }

    private static String mergeTemplates(String contents) {
        return getMailTemplatePrefix() + contents + getMailTemplatePostFix();
    }
}
