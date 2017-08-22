/**
  * Copyright 2017 bejson.com 
  */
package com.gw.conf.utils;
import java.util.List;

/**
 * Auto-generated: 2017-08-22 10:30:0
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JmeterAddr {

    private List<Slave> Slave;
    private Master Master;
    public void setSlave(List<Slave> Slave) {
         this.Slave = Slave;
     }
     public List<Slave> getSlave() {
         return Slave;
     }

    public void setMaster(Master Master) {
         this.Master = Master;
     }
     public Master getMaster() {
         return Master;
     }

}