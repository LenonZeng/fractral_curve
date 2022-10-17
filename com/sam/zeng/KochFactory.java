package com.sam.zeng;

import com.sam.zeng.koch.*;

public  class KochFactory {
      private IKochCurve mKoch;
      public IKochCurve createKoch(KochType type, int n){
          switch (type.getType()){
              case "line":
                  mKoch = new KochCurve(n,new Point(0,0), new Point(1,0));
                  break;
              case "triangle":
                  mKoch= new TriKochCurve(n, new Point(0,0), new Point(1,0), new Point(0.5, 0.5));
                  break;
              case "square":
                  mKoch=new SquareKochCurve();
                  break;
              case "star":
                  mKoch = new StarKochCurve();
                  break;
              case "snowflake":
                  mKoch=new SnowflakeKochCurve();
                  break;
              default:
                  throw new UnsupportedOperationException();
          }
          return mKoch;
      };
}
