<template>
    <div id="wei-dialog-detail">
        <slot name="rowHead"></slot>
        <el-row v-for="row in realRows" :key="row.label">
            <el-col :span="colNum">
                <template v-if="null != row.label && 7 < row.label.length">
                    <el-tooltip class="item" effect="dark" :content="row.label" placement="top">
                        <div class="label">{{row.label}}</div>
                    </el-tooltip>
                </template>
                <template v-else>
                    <div class="label">{{row.label || '标题'}}</div>
                </template>
            </el-col>
            <el-col :span="24 - colNum">
                <div class="content">
                    <!--自定义显示element-ui组件，属性详情请看element-ui官网-->
                    <template v-if="row.element">
                        <template v-if="'tag' === row.type">
                            <el-tag :type="row.element(row)['type'] || ''"
                                    :size="row.element(row)['size'] || 'mini'"
                                    :effect="row.element(row)['effect'] || 'dark'">
                                {{row.element(row)['content'] || row.prop}}
                            </el-tag>
                        </template>
                        <template v-else-if="'link' === row.type">
                            <el-link :target="row.element(row)['target'] || '_blank'"
                                     :href="row.element(row)['href'] || ''"
                                     :type="row.element(row)['type'] || ''"
                                     :icon="row.element(row)['icon'] || ''"
                                     :underline="row.element(row)['underline']">
                                {{row.element(row)['content'] || row.prop}}
                            </el-link>
                        </template>
                        <template v-else-if="'switch' === row.type">
                            <el-switch :value="row.element(row)['value'] || ''"
                                       :disabled="row.element(row)['disabled']"
                                       :activeColor="row.element(row)['activeColor'] || '#13ce66'"
                                       :inactiveColor="row.element(row)['inactiveColor'] || '#ff4949'"
                                       :activeText="row.element(row)['activeText'] || ''"
                                       :inactiveText="row.element(row)['inactiveText'] || ''"></el-switch>
                        </template>
                        <template v-else-if="'icon' === row.type">
                            <i :class="row.element(row)['leftIcon'] || ''"></i>
                            <span style="margin-left: 5px">{{row.element(row)['content']}}</span>
                            <i :class="row.element(row)['rightIcon'] || ''"></i>
                        </template>
                        <template v-else-if="'avatar' === row.type">
                            <div @click="avatarClick(row.element(row)['src'])">
                                <el-image :src="row.element(row)['src']"
                                          :lazy="!row.element(row)['notLazy']"
                                          :alt="row.element(row)['alt'] || ''"
                                          :fit="row.element(row)['fit'] || 'cover'"
                                          :style="row.element(row)['style'] || 'width:30px;height:30px'">
                                    <div slot="error">
                                        <i style="font-size: 21px;" class="el-icon-picture-outline"></i>
                                    </div>
                                </el-image>
                            </div>
                        </template>
                        <template v-else-if="'table' === row.type">
                            <wei-table :maxHeight="row.element(row)['height']"
                                       :tableData="row.element(row)['tableData'] || []"
                                       :tableColumns="row.element(row)['tableColumns'] || []"></wei-table>
                        </template>
                        <template v-else><h1 style="color: #ff4949;">{{row.label}}没有指定type</h1></template>
                    </template>
                    <template v-else>
                        <!--需要处理元素———:formatter=""-->
                        <template v-if="row.formatter">
                            <div v-html="row.formatter(row)"></div>
                        </template>
                        <!--表格普通元素-->
                        <template v-else>
                            <div>{{row.prop}}</div>
                        </template>
                    </template>
                </div>
            </el-col>
        </el-row>
        <slot name="rowTail"></slot>
        <div class="show">
            <el-dialog :visible.sync="dialogShowImage">
                <img width="100%" :src="dialogImageUrl">
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Index",
        components: {
            'wei-table': () => import('@/components/table/Simple.vue')
        },
        props: {
            rows: {
                type: Array,
                default: () => []
            },
            //左边站的格数
            colNum: {
                type: Number,
                default: 5
            }
        },
        computed: {
            /**
             * 真实现实的行
             * @returns {*}
             */
            realRows() {
                let {rows} = this;
                return rows.filter(item => !item.hidden);
            }
        },
        data() {
            return {
                //弹窗展示图片
                dialogShowImage: false,
                //弹窗展示图片的路径
                dialogImageUrl: ''
            }
        },
        methods: {
            //展示图片
            avatarClick(src) {
                this.dialogImageUrl = src;
                this.dialogShowImage = true;
            }
        }
    }
</script>

<style lang="scss">
    #wei-dialog-detail {
        overflow: hidden;

        .el-row {
            color: #666;
            margin-bottom: 5px;
        }

        .label {
            font-size: 0.9rem;
            background-color: #eee;
            text-align: center;
            height: 35px;
            line-height: 35px;
            overflow: hidden;
        }

        .content {
            font-size: 0.8rem;
            border: 1px solid #e2e2e2;
            padding: 7px;
            box-sizing: border-box;
            min-height: 35px;
        }
    }
</style>