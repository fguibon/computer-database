package com.excilys.binding;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.excilys.binding.mapper","com.excilys.binding.validator"})
public class BindingConfig {

}
