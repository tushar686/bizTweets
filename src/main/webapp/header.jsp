<div class="navbar navbar-default">
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="#">bizTweets</a>
  </div>
  <div class="navbar-collapse collapse navbar-responsive-collapse">
    <ul class="nav navbar-nav">
     <li><a href="#" onClick="loadTweets();">bizTweets</a></li>
     <li><a href="#" >Following</a></li>
     <li><a href="#" onClick="showEntities();">bizEntities</a></li>
    </ul>
    <div class="col-sm-3 col-md-3 pull-left">
        <div class="navbar-form">
        <div class="input-group">
            <input type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term">
            <div class="input-group-btn">
                <button class="btn btn-default" type="button" onClick="freshSearchTweets();"><i class="glyphicon glyphicon-search"></i></button>
            </div>
        </div>
        </div>
    </div>    
  </div>
</div>