function loadUserDetails() {
	restCall("GET", "/bizTweets/getUserFollowingEntities?user=tushar686@gmail.com", "", "json", "application/json", success_loadUserUserFollowingEntities);
}

function success_loadUserUserFollowingEntities(response) {
	global.usersFollowEntities = response;
}