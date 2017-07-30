/**
 * Copyright 2009-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rabbitframework.web.resources;

import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * 默认注册spring {@link RequestContextFilter}
 * 在web.xml配置servlet或Filter后，init-param中需要加上如下代码：
 * <pre>
 *   &lt;init-param&gt;
 *    &lt;param-name&gt; javax.ws.rs.Application &lt;/param-name&gt;
 *     &lt;param-value&gt; com.rabbitframework.web.resources.SpringRegisterApplicationConfig &lt;/param-value&gt;
 *   &lt;/init-param&gt;
 * </pre>
 *
 * @author: justin
 * @date: 2017-07-30 下午11:52
 */
public class SpringRegisterApplicationConfig extends ApplicationConfig {
    public SpringRegisterApplicationConfig() {
        super();
        register(RequestContextFilter.class);
    }
}
