package git.gitrest.api.dto;

import java.util.List;

public class ProjectionbyCodeSearchDTO {

    public List<CommitsDTO> getCommitsDTOList() {
        return commitsDTOList;
    }

    public void setCommitsDTOList(List<CommitsDTO> commitsDTOList) {
        this.commitsDTOList = commitsDTOList;
    }

    public CommitsUserDTO getCommitsUserDTO() {
        return commitsUserDTO;
    }

    public void setCommitsUserDTO(CommitsUserDTO commitsUserDTO) {
        this.commitsUserDTO = commitsUserDTO;
    }

    private List<CommitsDTO> commitsDTOList;
    private CommitsUserDTO commitsUserDTO;
}
