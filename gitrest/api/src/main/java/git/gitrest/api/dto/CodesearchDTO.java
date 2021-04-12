package git.gitrest.api.dto;

import java.util.List;

public class CodesearchDTO {

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public boolean isIncompleteresults() {
        return incompleteresults;
    }

    public void setIncompleteresults(boolean incompleteresults) {
        this.incompleteresults = incompleteresults;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    private int totalcount;
    private boolean incompleteresults;
    private List<Items> items;


    static public class Items{

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public Long getScore() {
            return score;
        }

        public void setScore(Long score) {
            this.score = score;
        }

        private String name;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getGiturl() {
            return giturl;
        }

        public void setGiturl(String giturl) {
            this.giturl = giturl;
        }

        public String getHtmlurl() {
            return htmlurl;
        }

        public void setHtmlurl(String htmlurl) {
            this.htmlurl = htmlurl;
        }

        public Repositoryitems getRepository() {
            return repository;
        }

        public void setRepository(Repositoryitems repository) {
            this.repository = repository;
        }

        private String path;
        private String sha;
        private String url;
        private String giturl;
        private String htmlurl;
        private Repositoryitems repository;
        private Long score;


    }

    static public class Repositoryitems{
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNodeid() {
            return nodeid;
        }

        public void setNodeid(String nodeid) {
            this.nodeid = nodeid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public boolean isPrivaterep() {
            return privaterep;
        }

        public void setPrivaterep(boolean privaterep) {
            this.privaterep = privaterep;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public String getHtmlurl() {
            return htmlurl;
        }

        public void setHtmlurl(String htmlurl) {
            this.htmlurl = htmlurl;
        }

        public String isDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isFork() {
            return fork;
        }

        public void setFork(boolean fork) {
            this.fork = fork;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getForksurl() {
            return forksurl;
        }

        public void setForksurl(String forksurl) {
            this.forksurl = forksurl;
        }

        public String getKeysurl() {
            return keysurl;
        }

        public void setKeysurl(String keysurl) {
            this.keysurl = keysurl;
        }

        public String getCollaboratorsurl() {
            return collaboratorsurl;
        }

        public void setCollaboratorsurl(String collaboratorsurl) {
            this.collaboratorsurl = collaboratorsurl;
        }

        public String getTeamsurl() {
            return teamsurl;
        }

        public void setTeamsurl(String teamsurl) {
            this.teamsurl = teamsurl;
        }

        public String getHooksurl() {
            return hooksurl;
        }

        public void setHooksurl(String hooksurl) {
            this.hooksurl = hooksurl;
        }

        public String getIssueeventsurl() {
            return issueeventsurl;
        }

        public void setIssueeventsurl(String issueeventsurl) {
            this.issueeventsurl = issueeventsurl;
        }

        public String getEventsurl() {
            return eventsurl;
        }

        public void setEventsurl(String eventsurl) {
            this.eventsurl = eventsurl;
        }

        public String getAssigneesurl() {
            return assigneesurl;
        }

        public void setAssigneesurl(String assigneesurl) {
            this.assigneesurl = assigneesurl;
        }

        public String getBranchesurl() {
            return branchesurl;
        }

        public void setBranchesurl(String branchesurl) {
            this.branchesurl = branchesurl;
        }

        public String getTagsurl() {
            return tagsurl;
        }

        public void setTagsurl(String tagsurl) {
            this.tagsurl = tagsurl;
        }

        public String getBlobsurl() {
            return blobsurl;
        }

        public void setBlobsurl(String blobsurl) {
            this.blobsurl = blobsurl;
        }

        public String getGittagsurl() {
            return gittagsurl;
        }

        public void setGittagsurl(String gittagsurl) {
            this.gittagsurl = gittagsurl;
        }

        public String getGitrefsurl() {
            return gitrefsurl;
        }

        public void setGitrefsurl(String gitrefsurl) {
            this.gitrefsurl = gitrefsurl;
        }

        public String getTreesurl() {
            return treesurl;
        }

        public void setTreesurl(String treesurl) {
            this.treesurl = treesurl;
        }

        public String getStatusesurl() {
            return statusesurl;
        }

        public void setStatusesurl(String statusesurl) {
            this.statusesurl = statusesurl;
        }

        public String getLanguagesurl() {
            return languagesurl;
        }

        public void setLanguagesurl(String languagesurl) {
            this.languagesurl = languagesurl;
        }

        public String getStargazersurl() {
            return stargazersurl;
        }

        public void setStargazersurl(String stargazersurl) {
            this.stargazersurl = stargazersurl;
        }

        public String getContributorsurl() {
            return contributorsurl;
        }

        public void setContributorsurl(String contributorsurl) {
            this.contributorsurl = contributorsurl;
        }

        public String getSubscribersurl() {
            return subscribersurl;
        }

        public void setSubscribersurl(String subscribersurl) {
            this.subscribersurl = subscribersurl;
        }

        public String getSubscriptionurl() {
            return subscriptionurl;
        }

        public void setSubscriptionurl(String subscriptionurl) {
            this.subscriptionurl = subscriptionurl;
        }

        public String getCommitsurl() {
            return commitsurl;
        }

        public void setCommitsurl(String commitsurl) {
            this.commitsurl = commitsurl;
        }

        public String getGitcommitsurl() {
            return gitcommitsurl;
        }

        public void setGitcommitsurl(String gitcommitsurl) {
            this.gitcommitsurl = gitcommitsurl;
        }

        public String getCommentsurl() {
            return commentsurl;
        }

        public void setCommentsurl(String commentsurl) {
            this.commentsurl = commentsurl;
        }

        public String getIssuecommenturl() {
            return issuecommenturl;
        }

        public void setIssuecommenturl(String issuecommenturl) {
            this.issuecommenturl = issuecommenturl;
        }

        public String getContentsurl() {
            return contentsurl;
        }

        public void setContentsurl(String contentsurl) {
            this.contentsurl = contentsurl;
        }

        public String getCompareurl() {
            return compareurl;
        }

        public void setCompareurl(String compareurl) {
            this.compareurl = compareurl;
        }

        public String getMergesurl() {
            return mergesurl;
        }

        public void setMergesurl(String mergesurl) {
            this.mergesurl = mergesurl;
        }

        public String getArchiveurl() {
            return archiveurl;
        }

        public void setArchiveurl(String archiveurl) {
            this.archiveurl = archiveurl;
        }

        public String getDownloadsurl() {
            return downloadsurl;
        }

        public void setDownloadsurl(String downloadsurl) {
            this.downloadsurl = downloadsurl;
        }

        public String getIssuesurl() {
            return issuesurl;
        }

        public void setIssuesurl(String issuesurl) {
            this.issuesurl = issuesurl;
        }

        public String getPullsurl() {
            return pullsurl;
        }

        public void setPullsurl(String pullsurl) {
            this.pullsurl = pullsurl;
        }

        public String getMilestonesurl() {
            return milestonesurl;
        }

        public void setMilestonesurl(String milestonesurl) {
            this.milestonesurl = milestonesurl;
        }

        public String getNotificationsurl() {
            return notificationsurl;
        }

        public void setNotificationsurl(String notificationsurl) {
            this.notificationsurl = notificationsurl;
        }

        public String getLabelsurl() {
            return labelsurl;
        }

        public void setLabelsurl(String labelsurl) {
            this.labelsurl = labelsurl;
        }

        public String getReleasesurl() {
            return releasesurl;
        }

        public void setReleasesurl(String releasesurl) {
            this.releasesurl = releasesurl;
        }

        public String getDeploymentsurl() {
            return deploymentsurl;
        }

        public void setDeploymentsurl(String deploymentsurl) {
            this.deploymentsurl = deploymentsurl;
        }

        private int id;
        private String nodeid;
        private String name;
        private String fullname;
        private boolean privaterep;
        private Owner owner;
        private String htmlurl;
        private String description;
        private boolean fork;
        private String url;
        private String forksurl;
        private String keysurl;
        private String collaboratorsurl;
        private String teamsurl;
        private String hooksurl;
        private String issueeventsurl;
        private String eventsurl;
        private String assigneesurl;
        private String branchesurl;
        private String tagsurl;
        private String blobsurl;
        private String gittagsurl;
        private String gitrefsurl;
        private String treesurl;
        private String statusesurl;
        private String languagesurl;
        private String stargazersurl;
        private String contributorsurl;
        private String subscribersurl;
        private String subscriptionurl;
        private String commitsurl;
        private String gitcommitsurl;
        private String commentsurl;
        private String issuecommenturl;
        private String contentsurl;
        private String compareurl;
        private String mergesurl;
        private String archiveurl;
        private String downloadsurl;
        private String issuesurl;
        private String pullsurl;
        private String milestonesurl;
        private String notificationsurl;
        private String labelsurl;
        private String releasesurl;
        private String deploymentsurl;

        }


        static public class Owner{

            public String getLogin() {
                return login;
            }

            public void setLogin(String login) {
                this.login = login;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNode_id() {
                return node_id;
            }

            public void setNode_id(String node_id) {
                this.node_id = node_id;
            }

            public String getAvatar_url() {
                return avatar_url;
            }

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }

            public String getGravatar_id() {
                return gravatar_id;
            }

            public void setGravatar_id(String gravatar_id) {
                this.gravatar_id = gravatar_id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getHtml_url() {
                return html_url;
            }

            public void setHtml_url(String html_url) {
                this.html_url = html_url;
            }

            public String getFollowers_url() {
                return followers_url;
            }

            public void setFollowers_url(String followers_url) {
                this.followers_url = followers_url;
            }

            public String getFollowing_url() {
                return following_url;
            }

            public void setFollowing_url(String following_url) {
                this.following_url = following_url;
            }

            public String getGists_url() {
                return gists_url;
            }

            public void setGists_url(String gists_url) {
                this.gists_url = gists_url;
            }

            public String getStarred_url() {
                return starred_url;
            }

            public void setStarred_url(String starred_url) {
                this.starred_url = starred_url;
            }

            public String getSubscriptions_url() {
                return subscriptions_url;
            }

            public void setSubscriptions_url(String subscriptions_url) {
                this.subscriptions_url = subscriptions_url;
            }

            public String getOrganizations_url() {
                return organizations_url;
            }

            public void setOrganizations_url(String organizations_url) {
                this.organizations_url = organizations_url;
            }

            public String getRepos_url() {
                return repos_url;
            }

            public void setRepos_url(String repos_url) {
                this.repos_url = repos_url;
            }

            public String getEvents_url() {
                return events_url;
            }

            public void setEvents_url(String events_url) {
                this.events_url = events_url;
            }

            public String getReceived_events_url() {
                return received_events_url;
            }

            public void setReceived_events_url(String received_events_url) {
                this.received_events_url = received_events_url;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public boolean isSite_admin() {
                return site_admin;
            }

            public void setSite_admin(boolean site_admin) {
                this.site_admin = site_admin;
            }

            private String login;
        private int id;
        private String node_id;
        private String avatar_url;
        private String gravatar_id;
        private String url;
        private String html_url;
        private String followers_url;
        private String following_url;
        private String gists_url;
        private String starred_url;
        private String subscriptions_url;
        private String organizations_url;
        private String repos_url;
        private String events_url;
        private String received_events_url;
        private String type;
        private boolean site_admin;
    }
}
