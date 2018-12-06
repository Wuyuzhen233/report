
package com.example.report.common.enums;

public enum ErrorCode {

  /**
   * (保留码) 表示未知异常
   */
  UNEXCEPTED(9999),

  /**
   * 预约看样失败
   */
  FAILORDER(8888),

  /* 公共模块错误码 */

  /**
   * 成功
   */
  SUCCESS(1000),

  /**
   * 用户未登录
   */
  USER_NOT_LOGIN(1001),

  /**
   * 认证令牌过期
   */
  AUTH_TOKEN_EXPIRE(1002),

  /**
   * 非法参数
   */
  ILLEGAL_PARAMETER(1003),

  /**
   * 权限不足
   */
  ACCESS_DENIED(1004),

  /**
   * 结果为空
   */
  RESULT_EMPTY(1005),

  /**
   * 提交表单为空
   */
  from_null(1007),

  /**
   * 参数为空
   */
  EMPTY_PARAMS(1008),

  /**
   * 没有库存了
   */
  NO_STOCKS(1009),

  /**
   * 操作数据库失败
   */
  FAIL_DATABASE(1006),

  /**
   * token为空或不存在
   */
  AUTH_TOKEN_EMPTY(1007),
  /**
   * token为空或不存在
   */
  AUTH_TOKEN_ERROR(1008),
  /* 登录模块 */

  /**
   * 用户不存在
   */
  USER_NOT_EXISTS(2001),

  /**
   * 验证码不正确
   */
  SMS_CODE_INCORRECT(2002),

  /**
   * 企业用户详情不存在
   */
  ORGANIZE_DETAIL_NOT_EXISTS(2003),

  /**
   * 个人用户详情不存在
   */
  USER_RDETAIL_NOT_EXISTS(2004),

  /**
   * 用户名不存在
   */
  USER_NAME_NOT_EXISTS(2005),

  /**
   * 密码错误
   */
  PASSWORD_IS_WRONG(2006),

  /**
   * 用户被禁用
   */
  USER_IS_BANNED(2007),
  /**
   * 刷新token失败
   */
  TOKEN_REFRESH_FAIL(2008),

  // 注册

  /**
   * 手机验证码错误
   */
  PHONE_CODE_IS_WRONG(6001),

  /**
   * 邮箱验证码错误
   */
  EMAIL_CODE_IS_WRONG(6002),

  /**
   * EXCEL解析异常
   */
  EXCEL_INVALIDATED(6003),

  /**
   * 发送短信验证码失败
   */
  PHONE_SEND_WRONG(6004),



  /* 文件模块 */

  /**
   * 上传的文件大小超限
   */
  FILE_SIZE_OVERRUN(3001),

  /**
   * 空文件
   */
  EMPTY_FILE(3002),

  /**
   * 文件处理失败
   */
  FAIL_FILE(3003),

  /**
   * 文件名为空
   */
  FILE_NAME_IS_EMPTY(3004),

  /**
   * 文件类型错误
   */
  FILE_TYPE_IS_WRONG(3005),

  /* 业务模块 */

  /**
   * 业务会议不存在
   */
  NOTARY_MEETING_NOT_EXIST(4002),

  /**
   * 用户不在参会列表
   */
  USER_NOT_IN_JOIN_USER_ID(4003),

  /**
   * 业务不存在
   */
  NOTARY_BUSINESS_NOT_EXIST(4004),

  /**
   * 不能操作当前记录
   */
  CAN_NOT_OPERATE_CURRENT_RECORD(4005),

  /**
   * 业务状态不正确
   */
  NOTARY_BUSINESS_STATUS_INCORRECT(4006),

  /* 公证模块 */

  /**
   * 公证要素不存在
   */
  NOTARY_BUSINESS_FACTOR_NOT_EXIST(4007),

  /**
   * 公证员不存在
   */
  NOTARY_USER_NOT_EXIST(4008),

  /**
   * 业务状态码错误
   */
  NOTARY_BUSINESS_STATUS_ERROR(4009),

  /**
   * 贷款人不能为申请人或者所选公证员
   */
  LOAN_USER_ERROR(4010),

  /**
   * 用户类型为空
   */
  USER_TYPE_IS_EMPTY(4011),

  /**
   * 用户id为空
   */
  USER_ID_IS_EMPTY(4012),

  /**
   * 手机号为空
   */
  MOBILE_PHONE_IS_EMPTY(4013),

  /**
   * 用户密码为空
   */
  PASSWORD_IS_EMPTY(4014),

  /**
   * 用户名为空
   */
  LOGIN_NAME_IS_EMPTY(4015),

  /**
   * 身份证为空
   */
  ID_CARD_IS_EMPTY(4016),


  /**
   * 组织机构编号为空
   */
  ORG_CODE_IS_EMPTY(4017),

  /**
   * 字典类型
   */
  DICT_TYPE_IS_EMPTY(4018),



  /* 后台管理模块 */

  /**
   * 手机号码已经存在
   */
  MOBILE_EXITS(5001),

  /**
   * 身份证和姓名不匹配
   */
  NAME_IDCARD_MISMATCHING(5002),

  /**
   * 字典码已经存在
   */
  DICTIONARY_CODE_EXITS(5003),

  /**
   * 用户名已经存在
   */
  LOGIN_NAME_EXITS(5004),

  /**
   * 手机号码为空
   */
  MOBILE_EMPTY(5005),

  /**
   * 密码为空
   */
  PASSWORD_EMPTY(5006),

  /**
   * 修改密码失败
   */
  CHANGE_PASSWORD_FAIL(5007),

  /**
   * 原密码失败
   */
  CHANGE_OLD_PASSWORD_ERROR(5007),

  /**
   * 机构名称已存在
   */
  ORGNAZATION_NAME_EXITS(5007),

  /**
   * 状态为开启
   */
  STATUS_ON(5008),

  /**
   * 状态为禁用
   */
  STATUS_OFF(5009),

  /**
   * 机构名称不一样
   */
  ORGNAZATION_NAME_MISMATCHING(5010),

  /**
   * 登录信息不完整
   */
  LOGIN_INFO_INCOMPLETE(5011),

  /**
   * 状态不能禁用
   */
  STATUS_CANNOTOFF(5012);

  private int value;

  private ErrorCode(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}

