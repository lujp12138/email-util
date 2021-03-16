package com.lujp.util.email.send.config;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Properties;

/**
 * 邮件配置类
 * @author lujp
 * @date 2021-03-02
 */
@Data
@Accessors(chain = true)
public class EmailConfig {

    /**
     * 私有构造方法，请使用 EmailConfigBuilder 创建实例
     */
    private EmailConfig() {
    }

    /**
     * 主机名
     */
    private String host;
    /**
     * 主机端口号
     */
    private String port;
    /**
     * 是否需要用户认证
     */
    private Boolean auth;
    /**
     * 是否启用TLS
     */
    private Boolean startTlsEnable;
    /**
     * 是否启用SSL
     */
    private Boolean sslEnable;
    /**
     * 是否debug
     */
    private Boolean debugEnable;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 构建成 Properties 对象
     * @return
     */
    public Properties properties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", auth == null ? null : auth.toString());
        props.put("mail.smtp.starttls.enable", startTlsEnable == null ? null : startTlsEnable.toString());
        props.put("mail.smtp.ssl.enable", sslEnable == null ? null : sslEnable.toString());
        return props;
    }

    /**
     * EmailConfig 建造者
     */
    @Data
    @Accessors(chain = true)
    public static class EmailConfigBuilder {
        /**
         * 主机名-QQ
         */
        public static final String HOST_QQ = "smtp.qq.com";
        /**
         * 主机端口号-QQ
         */
        public static final String PORT_QQ = "465";
        /**
         * 主机名-163
         */
        public static final String HOST_163 = "smtp.163.com";
        /**
         * 主机端口号-163
         */
        public static final String PORT_163 = "465";

        /**
         * 主机名，默认为QQ邮箱的主机名
         */
        private String host;
        /**
         * 主机端口号，默认为QQ邮箱的端口号
         */
        private String port;
        /**
         * 是否需要用户认证，默认为true
         */
        private Boolean auth;
        /**
         * 是否启用TLS，默认为true
         */
        private Boolean startTlsEnable;
        /**
         * 是否启用SSL，默认为true
         */
        private Boolean sslEnable;
        /**
         * 是否debug，默认为false
         */
        private Boolean debugEnable;
        /**
         * 用户名
         */
        private String username;
        /**
         * 密码
         */
        private String password;

        /**
         * 构建
         * @return
         */
        public EmailConfig build() {
            return defaultBuild();
        }

        /**
         * 设置为QQ邮箱的主机和端口
         * @return
         */
        public EmailConfigBuilder configQQ() {
            this.setHost(HOST_QQ);
            this.setPort(PORT_QQ);
            return this;
        }

        /**
         * 设置为163邮箱的主机和端口
         * @return
         */
        public EmailConfigBuilder config163() {
            this.setHost(HOST_163);
            this.setPort(PORT_163);
            return this;
        }

        private EmailConfig defaultBuild() {
            EmailConfig emailConfig = new EmailConfig();
            emailConfig.setHost(this.host == null ? HOST_QQ : this.host);
            emailConfig.setPort(this.port == null ? PORT_QQ : this.host);
            emailConfig.setAuth(this.auth == null ? true : this.auth);
            emailConfig.setStartTlsEnable(this.startTlsEnable == null ? true : this.startTlsEnable);
            emailConfig.setSslEnable(this.sslEnable == null ? true : this.sslEnable);
            emailConfig.setDebugEnable(this.debugEnable == null ? false : this.debugEnable);
            emailConfig.setUsername(this.username);
            emailConfig.setPassword(this.password);
            return emailConfig;
        }
    }
}
