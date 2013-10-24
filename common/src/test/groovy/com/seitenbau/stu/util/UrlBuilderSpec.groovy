package com.seitenbau.stu.util

import com.seitenbau.stu.util.UrlBuilder;

import spock.lang.*

class UrlBuilderSpec extends Specification
{
  
  UrlBuilder builder
  
  def baseUrl = "http://seitenbau.com"
  
  def setup()
  {
    builder = UrlBuilder.base(baseUrl)
  }
  
  def baseUrl()
  {
    when:
      def url = builder.get()
    then:
      url == baseUrl
  }
  
  def baseUrlIsNull()
  {
    when:
      builder = UrlBuilder.base(null)
    then:
      thrown(NullPointerException)
  }
  
  def UrlWithPathElement()
  {
    given:
      def pathElement = "contacts"
    when:
      builder.path(pathElement)
    then:
      builder.get() == "${baseUrl}/${pathElement}"
  }
  
  def UrlWithPathElements()
  {
    given:
      def pathElement = "contacts"
      def bSub = "bSub"
      def aSub = "aSub"
    when:
      builder.path(pathElement).path(bSub).path(aSub) 
    then:
      builder.get() == "${baseUrl}/${pathElement}/${bSub}/${aSub}"
  }
  
  def UrlWithNullPathElement()
  {
    given:
      def pathElement = "element"
    when:
      builder.path(null).path(pathElement)
    then:
      builder.get() == "${baseUrl}/${pathElement}"
  }
  
  def UrlWithPathAndParam()
  {
    given:
      def keyName = "key"
      def keyValue = "value"
      def resourceName = "resources"
    when:
      builder.path(resourceName).param(keyName, keyValue)
    then:
      builder.get() == "${baseUrl}/${resourceName}?${keyName}=${keyValue}"
  }
  
  def UrlWithParams()
  {
    given:
      def key01 = "key01"
      def key02 = "key02"
      def value01 = "value01"
      def value02 = "value02"
    when:
      builder.param(key01, value01).param(key02, value02)
    then:
      builder.get() == "${baseUrl}?${key01}=${value01}&${key02}=${value02}"
  }
}
