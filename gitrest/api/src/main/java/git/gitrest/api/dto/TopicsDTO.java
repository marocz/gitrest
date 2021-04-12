package git.gitrest.api.dto;

import java.util.List;

public class TopicsDTO {

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

        public String getDisplayname() {
            return displayname;
        }

        public void setDisplayname(String displayname) {
            this.displayname = displayname;
        }

        public String getShortdescription() {
            return shortdescription;
        }

        public void setShortdescription(String shortdescription) {
            this.shortdescription = shortdescription;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String getShortdescription) {
            this.description = getShortdescription;
        }

        public String getCreatedby() {
            return createdby;
        }

        public void setCreatedby(String createdby) {
            this.createdby = createdby;
        }

        public String getReleased() {
            return released;
        }

        public void setReleased(String released) {
            this.released = released;
        }

        public String getCreatedat() {
            return createdat;
        }

        public void setCreatedat(String createdat) {
            this.createdat = createdat;
        }

        public String getUpdatedat() {
            return updatedat;
        }

        public void setUpdatedat(String updatedat) {
            this.updatedat = updatedat;
        }

        public boolean isFeatured() {
            return featured;
        }

        public void setFeatured(boolean featured) {
            this.featured = featured;
        }

        public boolean isCurated() {
            return curated;
        }

        public void setCurated(boolean curated) {
            this.curated = curated;
        }

        public Long getScore() {
            return score;
        }

        public void setScore(Long score) {
            this.score = score;
        }

        private String name;
        private String displayname;
        private String shortdescription;
        private String description;
        private String createdby;
        private String released;
        private String createdat;
        private String updatedat;
        private boolean featured;
        private boolean curated;
        private Long score;

    }
}
