package com.kakaopay.project.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * EmptyUtil.java
 *
 * @version : 1.0
 * @author : lovelySub
 * @date : 2021-03-11
 * @Comment : Empty 비교 유틸
 */
public final class EmptyUtil {

  private EmptyUtil() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * isNotCollectionEmpty
   * 
   * @author : lovelySub
   * @date : 2021-03-11
   * @return : boolean
   * @Comment : collection empty 비교
   */
  public static <T> boolean isNotCollectionEmpty(final Collection<T> targetCollection) {
    return !isCollectionEmpty(targetCollection);
  }

  public static <T> boolean isCollectionEmpty(final Collection<T> targetCollection) {
    return targetCollection == null || targetCollection.isEmpty();
  }

  /**
   * MethodName : isNotMapEmpty
   * 
   * @author : lovelySub
   * @date : 2021-03-11
   * @return : boolean
   * @Comment : map empty 비교
   */
  public static boolean isNotMapEmpty(final Map<?, ?> targetMap) {
    return !isMapEmpty(targetMap);
  }

  public static boolean isMapEmpty(final Map<?, ?> targetMap) {
    return targetMap == null || targetMap.isEmpty();
  }

  /**
   * MethodName : isNotStringEmpty
   * 
   * @author : lovelySub
   * @date : 2021-03-11
   * @return : boolean
   * @Comment : String Empty 비교
   */
  public static boolean isNotStringEmpty(final String targetString) {
    return !isStringEmpty(targetString);
  }

  public static boolean isStringEmpty(final String targetString) {
    return targetString == null || targetString.isEmpty();
  }

}