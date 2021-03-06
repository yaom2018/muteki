package com.weiziplus.muteki.core.pc.common.token;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @author wanglongwei
 * @date 2020/07/30 15/31
 */
@Data
@Accessors(chain = true)
public class PcJwtExpand {

    /**
     * 系统用户的角色ids
     */
    private Set<Integer> roleIds;
}
