package org.idsp.smartengine.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @Function 流程分组
 */


@RestController
@RequestMapping("/isdp/se/SeGroup")
public class SeGroup {
	/**
	 * 查询
	 * @param user
	 * @return
	 */
	@GetMapping("/_query")
	public Mono<String> getUser(@PathVariable Long user) {
		return null;
	}

}
