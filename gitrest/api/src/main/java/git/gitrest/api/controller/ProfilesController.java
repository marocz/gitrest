package git.gitrest.api.controller;

import git.gitrest.api.dto.*;
import git.gitrest.api.entities.User;
import git.gitrest.api.model.Profile;
import git.gitrest.api.model.Profileadmin;
import git.gitrest.api.repository.ProfileRepository;
import git.gitrest.api.repository.UserRepository;
import git.gitrest.api.services.CreateNewAccount;
import git.gitrest.api.services.ProfileServices;
import git.gitrest.api.services.ProfileadminServices;
import org.apache.http.client.methods.*;
import org.json.*;
import org.slf4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@RequestMapping(path = "/git")
public class ProfilesController {

    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ProfileServices profileServices;
    @Autowired
    ProfileadminServices profileadminServices;

    @Autowired
    CreateNewAccount createNewAccount;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepository userService;
    private static Logger log = LoggerFactory.getLogger(ProfilesController.class);

    @GetMapping("")
    @ResponseBody
    public List<Profile> getAllProfiles(){
        return profileServices.getProfiles();
    }
    @PostMapping("/email")
    @ResponseBody
    public Profile getAllProfiles(@Valid @RequestBody Model1DTO profileDTO){


        return profileServices.getProfile(profileDTO.getEmail());
    }

    @PostMapping("/loggedusers")
    public Profile getloginuser(Model model, @RequestBody String1DTO string ) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String logname = loggedInUser.getName();

        User users = userRepository.findByUsername(string.getUsername());

        System.out.println(string.getUsername());
        System.out.println(users.getUsername());


        return profileServices.getProfile(users.getEmail());
    }

    @PostMapping("/loggedadmin")
    public Profileadmin getloginusers(Model model, @RequestBody String1DTO string ) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String logname = loggedInUser.getName();

        User users = userRepository.findByUsername(string.getUsername());

        System.out.println(string.getUsername());
        System.out.println(users.getUsername());

        return profileadminServices.getProfile(users.getEmail());
    }

    @PostMapping("/add")
    @ResponseBody
    public Profile addProfile(@Valid @RequestBody ProfileDTO profileDTO){

        return profileServices.addProfile(profileDTO);
    }

    @PostMapping("/addnewaccount")
    @ResponseBody
    public String addProfilesd(@Valid @RequestBody CreateNewAccountDTO profileDTO){
        try {
            if (!userService.findByUsername(profileDTO.getUsername()).getUsername().isEmpty() ) {
                try{
                    if (!userService.findByUsername(profileDTO.getUsername()).getUsername().isEmpty()){

                        return "username already exist";
                    }

                } catch (NullPointerException f){
                    return "username already exist";

                }


            }else {
                createNewAccount.addNewProfile(profileDTO);
            }
        }
        catch (NullPointerException e){
            try {
                if (!userService.findByEmail(profileDTO.getEmail()).getEmail().isEmpty()) {
                    return "email already exist";
                }
            }
            catch (NullPointerException j) {
                createNewAccount.addNewProfile(profileDTO);
            }
            return "user created successfully";
        }

        return "user created successfully";
    }

    @PostMapping("/phonecode")
    @ResponseBody
    public String addPhone(@Valid @RequestBody ProfileDTO profileDTO){

        return profileServices.addPhone(profileDTO);
    }

    @PostMapping("/changepasskey")
    @ResponseBody
    public String changePassword(@Valid @RequestBody ChangePassword profileDTO){

        return profileServices.changePassword(profileDTO);
    }

    @PostMapping("/setphonecode")
    @ResponseBody
    public String setPhone(@Valid @RequestBody ProfileDTO profileDTO){

        return profileServices.changePhone(profileDTO);
    }

    @PostMapping("/editprofile")
    @ResponseBody
    public Profile editPhone(@Valid @RequestBody EditProfileDTO profileDTO){

        return profileServices.editProfile(profileDTO);
    }

    @PostMapping("/editprofileadmin")
    @ResponseBody
    public Profileadmin editPhoneadmin(@Valid @RequestBody EditProfileDTO profileDTO){

        return profileadminServices.editProfileadmin(profileDTO);
    }

    @PostMapping("/changeavatar")
    @ResponseBody
    public Profile editAvatars(@Valid @RequestBody EditProfileDTO2 profileDTO){

        return profileServices.editAvatar(profileDTO);
    }

    @PostMapping("/changeavataradmin")
    @ResponseBody
    public Profileadmin editAvatarsadmin(@Valid @RequestBody EditProfileDTO2 profileDTO){

        return profileadminServices.editAvataradmin(profileDTO);
    }

    @GetMapping("/getprojectbytopics")
    @ResponseBody
    public ResponseEntity<TopicsDTO> getProjectbytopics(@Valid @RequestBody String2DTO request) throws java.net.ProtocolException{
        String urlstring = "";


        if(request.getFeatured() != null){
            urlstring = "https://api.github.com/search/topics?q=" +  request.getTopics() + "+is:featured";
        }else{
            urlstring = "https://api.github.com/search/topics?q=" +  request.getTopics();
        }
        TopicsDTO topicsDTO = new TopicsDTO();

    try {

        log.info(urlstring);
        URL url = new URL(urlstring);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");


        con.setRequestProperty("Accept", "application/vnd.github.mercy-preview+json");

        con.connect();

        int responsecode = con.getResponseCode();

        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {

            String inline = "";

            Scanner scanner = new Scanner(con.getInputStream());

            while (scanner.hasNext()) {
                inline += scanner.nextLine();

            }

            scanner.close();

            org.springframework.boot.configurationprocessor.json.JSONObject data_obj = new org.springframework.boot.configurationprocessor.json.JSONObject(inline);

            int total_count = (int) data_obj.optInt("total_count");
            topicsDTO.setTotalcount(total_count);
            boolean incomplete_results = data_obj.getBoolean("incomplete_results");
            topicsDTO.setIncompleteresults(incomplete_results);
            List<TopicsDTO.Items> itemsList = new ArrayList<>();
            org.springframework.boot.configurationprocessor.json.JSONArray itemsarray = data_obj.getJSONArray("items");
            int len1 =  itemsarray.length();
            System.out.println(len1);

            for (int i1 = 0; i1 < len1; i1++) {
                TopicsDTO.Items items = new TopicsDTO.Items();
                System.out.println("start");
                org.springframework.boot.configurationprocessor.json.JSONObject itemsarrayJSONObject = itemsarray.getJSONObject(i1);
                System.out.println("start");
                String name = (String) itemsarrayJSONObject.getString("name");
                String display_name = (String) itemsarrayJSONObject.getString("display_name");
                String short_description = (String) itemsarrayJSONObject.getString("short_description");
                String description = (String) itemsarrayJSONObject.getString("description");
                String created_by = (String) itemsarrayJSONObject.getString("created_by");
                String released = (String) itemsarrayJSONObject.getString("released");
                String created_at = (String) itemsarrayJSONObject.getString("created_at");
                String updated_at = (String) itemsarrayJSONObject.getString("updated_at");
                int score = (int) itemsarrayJSONObject.optInt("score");
                Long scorevalue = Long.valueOf(score);

                boolean featured = itemsarrayJSONObject.getBoolean("featured");
                boolean curated = itemsarrayJSONObject.getBoolean("curated");
                items.setName(name);
                items.setDisplayname(display_name);
                items.setShortdescription(short_description);
                items.setDescription(description);
                items.setCreatedby(created_by);
                items.setReleased(released);
                items.setCreatedat(created_at);
                items.setUpdatedat(updated_at);
                items.setFeatured(featured);
                items.setCurated(curated);
                items.setScore(scorevalue);


                itemsList.add(items);

                System.out.println(name);

            }
            topicsDTO.setItems(itemsList);
        }

        return new ResponseEntity<>(topicsDTO, HttpStatus.OK);
    }

        catch (Exception e){
            return new ResponseEntity<>(topicsDTO, HttpStatus.GATEWAY_TIMEOUT);
        }
    }

    @PostMapping("/getprojectbycode")
    @ResponseBody
    public ResponseEntity<CodesearchDTO> getProjectbyCode(@Valid @RequestBody String2DTO request)  throws java.net.ProtocolException{

        CodesearchDTO  codesearchDTO = new CodesearchDTO();
        String urlstring = "";
        if(request.getAccesstoken() != null){
            urlstring = "https://api.github.com/search/code?q=" +  request.getCode() + "&access_token=" + request.getAccesstoken();
        }else{
            urlstring = "https://api.github.com/search/code?q=" +  request.getCode() + "+user%3A" + request.getOrgs();
        }
        try{
                log.info(urlstring);
                URL url = new URL(urlstring);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                con.setRequestProperty("accept", "application/vnd.github.mercy-preview+json");

                con.connect();

                int responsecode = con.getResponseCode();
                if (responsecode != 200) {
                    throw new RuntimeException("HttpResponseCode: " + responsecode);
                } else {

                    String inline = "";
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNext()) {
                        inline += scanner.nextLine();
                    }

                    scanner.close();

                    org.springframework.boot.configurationprocessor.json.JSONObject data_obj = new org.springframework.boot.configurationprocessor.json.JSONObject(inline);

                    int total_count = (int) data_obj.optInt("total_count");
                    codesearchDTO.setTotalcount(total_count);
                    boolean incomplete_results = data_obj.getBoolean("incomplete_results");
                    codesearchDTO.setIncompleteresults(incomplete_results);
                    org.springframework.boot.configurationprocessor.json.JSONArray itemsarray = data_obj.getJSONArray("items");
                    List<CodesearchDTO.Items> itemsList = new ArrayList<>();
                    int len1 =  itemsarray.length();
                    System.out.println(len1);

                    for(int i1 = 0; i1 < len1; i1++) {
                        System.out.println("start");
                        CodesearchDTO.Items items = new CodesearchDTO.Items();
                        org.springframework.boot.configurationprocessor.json.JSONObject itemsDTO = itemsarray.getJSONObject(i1);
                        System.out.println("start");

                        String name = (String) itemsDTO.getString("name");
                        String path = (String) itemsDTO.getString("path");
                        String sha = (String) itemsDTO.getString("sha");
                        String url2 = (String) itemsDTO.getString("url");
                        String git_url = (String) itemsDTO.getString("git_url");
                        String html_url = (String) itemsDTO.getString("html_url");

                        log.info(name);
                        log.info("name");
                        System.out.println("name" + name);
                        items.setName(name);
                        items.setGiturl(git_url);
                        items.setHtmlurl(html_url);
                        items.setPath(path);

                        items.setSha(sha);
                  //      org.springframework.boot.configurationprocessor.json.JSONObject reparray = itemsDTO.JSONObject("repository");

//                        int len2 = reparray.length();
//                        System.out.println(len2);
                        int score = (int) itemsDTO.optInt("score");
                        Long scorevalue = Long.valueOf(score);


                        items.setScore(scorevalue);
                        items.setUrl(url2);


                        CodesearchDTO.Repositoryitems repositoryitems = new CodesearchDTO.Repositoryitems();

                        for(int i2 = 0; i2 < 1; i2++){
                            org.springframework.boot.configurationprocessor.json.JSONObject repobject = itemsDTO.getJSONObject("repository");
                            repositoryitems = new CodesearchDTO.Repositoryitems();

                            String namerep = (String) repobject.getString("name");
                            String node_id = (String) repobject.getString("node_id");
                            int id = (int) repobject.optInt("id");
                            String full_name = (String) repobject.getString("full_name");
                            boolean privaterep = repobject.getBoolean("private");
                            String html_urlrep = (String) repobject.getString("html_url");
                            String description = (String) repobject.getString("description");
                            boolean fork = repobject.getBoolean("fork");
                            String urlrep = (String) repobject.getString("url");
                            String forks_url = (String) repobject.getString("forks_url");
                            String keys_url = (String) repobject.getString("keys_url");
                            String collaborators_url = (String) repobject.getString("collaborators_url");
                            String teams_url = (String) repobject.getString("teams_url");
                            String hooks_url = (String) repobject.getString("hooks_url");
                            String issue_events_url = (String) repobject.getString("issue_events_url");
                            String events_url = (String) repobject.getString("events_url");
                            String assignees_url = (String) repobject.getString("assignees_url");
                            String branches_url = (String) repobject.getString("branches_url");
                            String tags_url = (String) repobject.getString("tags_url");
                            String blobs_url = (String) repobject.getString("blobs_url");
                            String git_tags_url = (String) repobject.getString("git_tags_url");
                            String git_refs_url = (String) repobject.getString("git_refs_url");
                            String trees_url = (String) repobject.getString("trees_url");
                            String statuses_url = (String) repobject.getString("statuses_url");
                            String languages_url = (String) repobject.getString("languages_url");
                            String stargazers_url = (String) repobject.getString("stargazers_url");
                            String contributors_url = (String) repobject.getString("contributors_url");
                            String subscribers_url = (String) repobject.getString("subscribers_url");
                            String subscription_url = (String) repobject.getString("subscription_url");
                            String commits_url = (String) repobject.getString("commits_url");
                            String git_commits_url = (String) repobject.getString("git_commits_url");
                            String comments_url = (String) repobject.getString("comments_url");
                            String issue_comment_url = (String) repobject.getString("issue_comment_url");
                            String contents_url = (String) repobject.getString("contents_url");
                            String compare_url = (String) repobject.getString("compare_url");
                            String merges_url = (String) repobject.getString("merges_url");
                            String archive_url = (String) repobject.getString("archive_url");
                            String downloads_url = (String) repobject.getString("downloads_url");
                            String issues_url = (String) repobject.getString("issues_url");
                            String pulls_url = (String) repobject.getString("pulls_url");
                            String milestones_url = (String) repobject.getString("milestones_url");
                            String notifications_url = (String) repobject.getString("notifications_url");
                            String labels_url = (String) repobject.getString("labels_url");
                            String releases_url = (String) repobject.getString("releases_url");
                            String deployments_url = (String) repobject.getString("deployments_url");
                            log.info(namerep);
                            repositoryitems.setArchiveurl(archive_url);
                            repositoryitems.setAssigneesurl(assignees_url);
                            repositoryitems.setBlobsurl(blobs_url);
                            repositoryitems.setBranchesurl(branches_url);
                            repositoryitems.setCollaboratorsurl(collaborators_url);
                            repositoryitems.setCommentsurl(comments_url);
                            repositoryitems.setCommitsurl(commits_url);
                            repositoryitems.setCompareurl(compare_url);
                            repositoryitems.setContentsurl(contents_url);
                            repositoryitems.setContributorsurl(contributors_url);
                            repositoryitems.setDeploymentsurl(deployments_url);
                            repositoryitems.setDescription(description);
                            repositoryitems.setDownloadsurl(downloads_url);
                            repositoryitems.setEventsurl(events_url);
                            repositoryitems.setFork(fork);
                            repositoryitems.setForksurl(forks_url);
                            repositoryitems.setFullname(full_name);
                            repositoryitems.setGitcommitsurl(git_commits_url);
                            repositoryitems.setGitrefsurl(git_refs_url);
                            repositoryitems.setGittagsurl(git_tags_url);
                            repositoryitems.setHooksurl(hooks_url);
                            repositoryitems.setHtmlurl(html_urlrep);
                            repositoryitems.setId(id);
                            repositoryitems.setIssuecommenturl(issue_comment_url);
                            repositoryitems.setIssueeventsurl(issue_events_url);
                            repositoryitems.setIssuesurl(issues_url);
                            repositoryitems.setKeysurl(keys_url);
                            repositoryitems.setLabelsurl(labels_url);
                            repositoryitems.setLanguagesurl(languages_url);
                            repositoryitems.setMergesurl(merges_url);
                            repositoryitems.setMilestonesurl(milestones_url);
                            repositoryitems.setName(namerep);
                            repositoryitems.setNodeid(node_id);
                            repositoryitems.setNotificationsurl(notifications_url);
                            repositoryitems.setPrivaterep(privaterep);
                            repositoryitems.setPullsurl(pulls_url);
                            repositoryitems.setReleasesurl(releases_url);
                            repositoryitems.setStargazersurl(stargazers_url);
                            repositoryitems.setStatusesurl(statuses_url);
                            repositoryitems.setSubscribersurl(subscribers_url);
                            repositoryitems.setSubscriptionurl(subscription_url);
                            repositoryitems.setTagsurl(tags_url);
                            repositoryitems.setTeamsurl(teams_url);
                            repositoryitems.setTreesurl(trees_url);
                            repositoryitems.setUrl(urlrep);

                            CodesearchDTO.Owner itemsowner = new CodesearchDTO.Owner();


//                            org.springframework.boot.configurationprocessor.json.JSONArray ownerarray = repobject.getJSONArray("owner");
//                            int len3 = ownerarray.length();
                            for(int i3 = 0; i3 < 1; i3++){

                                org.springframework.boot.configurationprocessor.json.JSONObject ownerobject = repobject.getJSONObject("owner");

                                String login = (String) ownerobject.getString("login");
                                String node_idowner = (String) ownerobject.getString("node_id");
                                int idowner = (int) ownerobject.optInt("id");
                                String avatar_url = (String) ownerobject.getString("avatar_url");
                                String gravatar_id = (String) ownerobject.getString("gravatar_id");
                                String urlowner = (String) ownerobject.getString("url");
                                String html_urlowner = (String) ownerobject.getString("html_url");
                                String followers_url = (String) ownerobject.getString("followers_url");
                                String following_url = (String) ownerobject.getString("following_url");
                                String gists_url = (String) ownerobject.getString("gists_url");
                                String starred_url = (String) ownerobject.getString("starred_url");
                                String subscriptions_url = (String) ownerobject.getString("subscriptions_url");
                                String organizations_url = (String) ownerobject.getString("organizations_url");
                                String repos_url = (String) ownerobject.getString("repos_url");
                                String events_urlowner = (String) ownerobject.getString("events_url");
                                String received_events_url = (String) ownerobject.getString("received_events_url");
                                String type = (String) ownerobject.getString("type");
                                boolean site_admin = ownerobject.getBoolean("site_admin");

                                itemsowner.setAvatar_url(avatar_url);
                                itemsowner.setEvents_url(events_urlowner);
                                itemsowner.setFollowers_url(followers_url);
                                itemsowner.setFollowing_url(following_url);
                                itemsowner.setGists_url(gists_url);
                                itemsowner.setGravatar_id(gravatar_id);
                                itemsowner.setHtml_url(html_urlowner);
                                itemsowner.setId(idowner);
                                itemsowner.setLogin(login);
                                itemsowner.setNode_id(node_idowner);
                                itemsowner.setOrganizations_url(organizations_url);
                                itemsowner.setReceived_events_url(received_events_url);
                                itemsowner.setRepos_url(repos_url);
                                itemsowner.setSite_admin(site_admin);
                                itemsowner.setStarred_url(starred_url);
                                itemsowner.setSubscriptions_url(subscriptions_url);
                                itemsowner.setType(type);
                                itemsowner.setUrl(urlowner);

                               
                                repositoryitems.setOwner(itemsowner);

                            }


                        }
                        items.setRepository(repositoryitems);
                        itemsList.add(items);

                    }

                    codesearchDTO.setItems(itemsList);
                }

                return new ResponseEntity<>(codesearchDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(codesearchDTO, HttpStatus.GATEWAY_TIMEOUT);
        }

    }

    @PostMapping("/getprojectbycommit")
    @ResponseBody
    public ResponseEntity<ProjectionbyCodeSearchDTO> getProjectCommits(@Valid @RequestBody String2DTO request)  throws java.net.ProtocolException{
        ProjectionbyCodeSearchDTO projectionbyCodeSearchDTO = new ProjectionbyCodeSearchDTO();
        List<CommitsDTO> commitsDTOList = new ArrayList<>();

        String urlstring = "";
        if(request.getUrlstring() != null){
            urlstring = request.getUrlstring();
        }else if(request.getOrgs() != null && request.getSha() != null && request.getCode() != null){
            urlstring = "https://api.github.com/repos/" +  request.getOrgs() + "/" + request.getCode() + "/commits/" + request.getSha();
        }else if (request.getOrgs() != null && request.getCode() != null && request.getSha() == null){
            urlstring = "https://api.github.com/repos/" +  request.getOrgs() + "/" +  request.getCode() + "/commits";
        }else{
            urlstring = "";
        }
        try{
            log.info(urlstring);
            URL url = new URL(urlstring);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("accept", "application/vnd.github.mercy-preview+json");

            con.connect();

            int responsecode = con.getResponseCode();
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                System.out.println(inline);

                scanner.close();

       //         final ObjectMapper objectMapper = new ObjectMapper();

             //   org.springframework.boot.configurationprocessor.json.JSONObject data_obj = objectMapper.readValue(inline, org.springframework.boot.configurationprocessor.json.JSONObject.class);

                System.out.println("Start");
           //     org.springframework.boot.configurationprocessor.json.JSONObject data_obj = new org.springframework.boot.configurationprocessor.json.JSONObject(inline);


                org.springframework.boot.configurationprocessor.json.JSONArray objarray = new org.springframework.boot.configurationprocessor.json.JSONArray(inline);

                int len1 = objarray.length();

                for (int i1 = 0; i1 < len1; i1++) {
                    CommitsDTO items = new CommitsDTO();
                    org.springframework.boot.configurationprocessor.json.JSONObject itemsobj = objarray.getJSONObject(i1);

                    org.springframework.boot.configurationprocessor.json.JSONObject authors = new org.springframework.boot.configurationprocessor.json.JSONObject();
                    org.springframework.boot.configurationprocessor.json.JSONObject committers = new org.springframework.boot.configurationprocessor.json.JSONObject();
                    System.out.println("authot catch null" + itemsobj.getString("author"));
                   // !itemsobj.getString("author").equals(null) ||
                    if(!itemsobj.isNull("author") == true) {
                        authors = itemsobj.getJSONObject("author");
                        System.out.println("authot catch null");
                    }

                    System.out.println("authot catch null");
                    if(!itemsobj.isNull("committer") == true) {
                        committers = itemsobj.getJSONObject("committer");
                    }
                    org.springframework.boot.configurationprocessor.json.JSONObject commit1 = itemsobj.getJSONObject("commit");


                    org.springframework.boot.configurationprocessor.json.JSONArray parents = itemsobj.getJSONArray("parents");
                    System.out.println("in object");
//                    int len2 = commit1.length();
//                    int len3 = authors.length();
//                    int len4 = committers.length();
                    int len5 = parents.length();

                    List<CommitsDTO.Parents> commitparent = new ArrayList<>();
                    String sha = (String) itemsobj.getString("sha");
                    String node_id = (String) itemsobj.getString("node_id");
                    String url2 = (String) itemsobj.getString("url");
                    String html_url = (String) itemsobj.getString("html_url");

                    items.setNodeid(node_id);
                    items.setHtmlurl(html_url);
                    items.setCommentsurl(sha);
                    items.setUrl(url2);

                    CommitsDTO.Commit commit = new CommitsDTO.Commit();

                    for (int i2 = 0; i2 < 1; i2++) {

                        org.springframework.boot.configurationprocessor.json.JSONObject authorobject =  commit1.getJSONObject("author");

                        org.springframework.boot.configurationprocessor.json.JSONObject committerobject = commit1.getJSONObject("committer");

                        org.springframework.boot.configurationprocessor.json.JSONObject treeobject = commit1.getJSONObject("tree");

                        org.springframework.boot.configurationprocessor.json.JSONObject verifiedobject = commit1.getJSONObject("verification");
                        System.out.println("in commit");
                        commit = new CommitsDTO.Commit();

                        String message = (String) commit1.getString("message");
                        String urlcommit = (String) commit1.getString("url");
                        int comment_count = (int) commit1.optInt("comment_count");

                        commit.setMessage(message);
                        commit.setUrl(urlcommit);
                        commit.setCommentcount(comment_count);

                     //   int lenauthor =  authorarray.length();

                        for (int lena1 = 0; lena1 < 1; lena1++) {
                            CommitsDTO.Profilecommit author = new CommitsDTO.Profilecommit();

                     //       org.springframework.boot.configurationprocessor.json.JSONObject authorobject = authorarray.getJSONObject(lena1);

                            String name = (String) authorobject.getString("name");
                            String email = (String) authorobject.getString("email");
                            String date = (String) authorobject.getString("date");
                            System.out.println("in author commit");
                            author.setName(name);
                            author.setDate(email);
                            author.setEmail(date);

                            commit.setAuthor(author);
                        }

                        int lencommiter =  1;

                        for (int lenc1 = 0; lenc1 < 1; lenc1++) {
                            CommitsDTO.Profilecommit committer = new CommitsDTO.Profilecommit();

                          //  org.springframework.boot.configurationprocessor.json.JSONObject committerobject = committerarray.getJSONObject(lenc1);

                            String name = (String) committerobject.getString("name");
                            String email = (String) committerobject.getString("email");
                            String date = (String) committerobject.getString("date");
                            System.out.println("in committer");
                            committer.setName(name);
                            committer.setDate(date);
                            committer.setEmail(email);

                            commit.setCommitter(committer);


                        }

                        int lentree =  1;

                        for (int lent1 = 0; lent1 < lentree; lent1++) {
                            CommitsDTO.Tree tree = new CommitsDTO.Tree();

                      //      org.springframework.boot.configurationprocessor.json.JSONObject treeobject = treearray.getJSONObject(lent1);

                            String shatree = (String) treeobject.getString("sha");
                            String urltree = (String) treeobject.getString("url");
                            System.out.println("in tree");
                            tree.setSha(shatree);
                            tree.setUrl(urltree);

                            commit.setTree(tree);

                        }

                        int lenverification =  1;

                        for (int lenv1 = 0; lenv1 < lenverification; lenv1++) {
                            CommitsDTO.Verification verification = new CommitsDTO.Verification();

                       //     org.springframework.boot.configurationprocessor.json.JSONObject verifiedobject = treearray.getJSONObject(lenv1);

                            String reason = (String) verifiedobject.getString("reason");
                            String signature = (String) verifiedobject.getString("signature");
                            String payload = (String) verifiedobject.getString("payload");
                            boolean verified = verifiedobject.getBoolean("verified");
                            System.out.println("in verification");
                            verification.setPayload(payload);
                            verification.setReason(reason);
                            verification.setSignature(signature);
                            verification.setVerified(verified);


                            commit.setVerification(verification);

                        }
                        items.setCommit(commit);
                    }

                    for (int a1 = 0; a1 < 1; a1++) {
                        CommitsDTO.Admin authordto = new CommitsDTO.Admin();
                        if(authors.length() != 0) {
                        org.springframework.boot.configurationprocessor.json.JSONObject authorobject = authors;
                        System.out.println("in authors");

                            String login = (String) authorobject.getString("login");
                            int id = (int) authorobject.optInt("id");
                            String nodeid = (String) authorobject.getString("node_id");
                            String avatar_url = (String) authorobject.getString("avatar_url");
                            String gravatar_id = (String) authorobject.getString("gravatar_id");
                            String urlauthor = (String) authorobject.getString("url");
                            String htmlurla = (String) authorobject.getString("html_url");
                            String followersurl = (String) authorobject.getString("followers_url");
                            String followingurl = (String) authorobject.getString("following_url");
                            String gistsurl = (String) authorobject.getString("gists_url");
                            String starredurl = (String) authorobject.getString("starred_url");
                            String subscriptionsurl = (String) authorobject.getString("subscriptions_url");
                            String organizationsurl = (String) authorobject.getString("organizations_url");
                            String reposurl = (String) authorobject.getString("repos_url");
                            String eventsurl = (String) authorobject.getString("events_url");
                            String receivedeventsurl = (String) authorobject.getString("received_events_url");
                            String typeauthor = (String) authorobject.getString("type");
                            boolean site_admin = authorobject.getBoolean("site_admin");

                            authordto.setAvatar_url(avatar_url);
                            authordto.setEvents_url(eventsurl);
                            authordto.setFollowers_url(followersurl);
                            authordto.setFollowing_url(followingurl);
                            authordto.setGists_url(gistsurl);
                            authordto.setGravatar_id(gravatar_id);
                            authordto.setHtml_url(htmlurla);
                            authordto.setId(id);
                            authordto.setLogin(login);
                            authordto.setNode_id(nodeid);
                            authordto.setOrganizations_url(organizationsurl);
                            authordto.setReceived_events_url(receivedeventsurl);
                            authordto.setRepos_url(reposurl);
                            authordto.setSite_admin(site_admin);
                            authordto.setStarred_url(starredurl);
                            authordto.setSubscriptions_url(subscriptionsurl);
                            authordto.setType(typeauthor);
                            authordto.setUrl(urlauthor);

                            items.setAuthor(authordto);
                        }else{
                            items.setAuthor(authordto);
                        }

                    }

                    for (int c1 = 0; c1 < 1; c1++) {
                        CommitsDTO.Admin commiterdto = new CommitsDTO.Admin();
                        if(committers.length() != 0) {
                        org.springframework.boot.configurationprocessor.json.JSONObject comitterobject = committers;


                            String login = (String) comitterobject.getString("login");
                            int id = (int) comitterobject.optInt("id");
                            String nodeid = (String) comitterobject.getString("node_id");
                            String avatar_url = (String) comitterobject.getString("avatar_url");
                            String gravatar_id = (String) comitterobject.getString("gravatar_id");
                            String urlauthor = (String) comitterobject.getString("url");
                            String htmlurla = (String) comitterobject.getString("html_url");
                            String followersurl = (String) comitterobject.getString("followers_url");
                            String followingurl = (String) comitterobject.getString("following_url");
                            String gistsurl = (String) comitterobject.getString("gists_url");
                            String starredurl = (String) comitterobject.getString("starred_url");
                            String subscriptionsurl = (String) comitterobject.getString("subscriptions_url");
                            String organizationsurl = (String) comitterobject.getString("organizations_url");
                            String reposurl = (String) comitterobject.getString("repos_url");
                            String eventsurl = (String) comitterobject.getString("events_url");
                            String receivedeventsurl = (String) comitterobject.getString("received_events_url");
                            String typeauthor = (String) comitterobject.getString("type");
                            boolean site_admin = comitterobject.getBoolean("site_admin");
                            System.out.println("committer");
                            commiterdto.setAvatar_url(avatar_url);
                            commiterdto.setEvents_url(eventsurl);
                            commiterdto.setFollowers_url(followersurl);
                            commiterdto.setFollowing_url(followingurl);
                            commiterdto.setGists_url(gistsurl);
                            commiterdto.setGravatar_id(gravatar_id);
                            commiterdto.setHtml_url(htmlurla);
                            commiterdto.setId(id);
                            commiterdto.setLogin(login);
                            commiterdto.setNode_id(nodeid);
                            commiterdto.setOrganizations_url(organizationsurl);
                            commiterdto.setReceived_events_url(receivedeventsurl);
                            commiterdto.setRepos_url(reposurl);
                            commiterdto.setSite_admin(site_admin);
                            commiterdto.setStarred_url(starredurl);
                            commiterdto.setSubscriptions_url(subscriptionsurl);
                            commiterdto.setType(typeauthor);
                            commiterdto.setUrl(urlauthor);

                            items.setCommitter(commiterdto);
                        }else{
                            items.setCommitter(commiterdto);
                        }

                    }

                    for (int p1 = 0; p1 < len5; p1++) {
                        CommitsDTO.Parents parents1 = new CommitsDTO.Parents();

                        org.springframework.boot.configurationprocessor.json.JSONObject parentobject = parents.getJSONObject(p1);

                        String shatree = (String) parentobject.getString("sha");
                        String urltree = (String) parentobject.getString("url");
                        String html_urlparent = (String) parentobject.getString("html_url");
                        System.out.println("parents");
                        parents1.setSha(shatree);
                        parents1.setUrl(urltree);
                        parents1.setHtml_url(html_urlparent);

                        commitparent.add(parents1);
                    }
                    items.setParents(commitparent);

                    commitsDTOList.add(items);
                }

                projectionbyCodeSearchDTO.setCommitsDTOList(commitsDTOList);

                CommitsUserDTO commitsUserDTO = new CommitsUserDTO();
                List<CommitsUserDTO.CommitUsers> commitUsersList = new ArrayList<>();
                System.out.println(commitsDTOList);
                int weekone = 0;
                int weektwo = 0;
                int weekthree = 0;
                int weekfour = 0;
                System.out.println("commitdto" + commitsDTOList.size());
                for (int n = 0; n < commitsDTOList.size(); n++){
                    CommitsDTO commitsDTO = commitsDTOList.get(n);
                    int commitid = commitsDTO.getCommitter().getId();
                    System.out.println("user list size " + commitUsersList.size());

                    int length = commitUsersList.size();
                    int nextquickly = 0;
                    if(length == 0){
                        length = 1;
                        nextquickly = 1;
                    }
                    System.out.println("commitdto " + commitsDTOList.size());

                    System.out.println(length);
                    int check = 0;
                    for (int i = 0; i < length; i++){
                        check = i + 1;
                        if(nextquickly != 1) {
                            if (commitUsersList.get(i).getId() == commitid) {
                                System.out.println("If Loop");
                                System.out.println(commitid);
                                int count = commitUsersList.get(i).getCount();
                                System.out.println("count is - " + count);
                                count = count + 1;
                                commitUsersList.get(i).setCount(count);
                                if (count > 0 && count < 5) {
                                    commitUsersList.get(i).setImpact("Low");
                                } else if (count > 5 && count < 9) {
                                    commitUsersList.get(i).setImpact("Medium");
                                } else if (count > 10) {
                                    commitUsersList.get(i).setImpact("High");
                                } else {
                                    commitUsersList.get(i).setImpact("Low");
                                }

                            } else if (check == length) {
                                System.out.println("If Loop");
                                CommitsUserDTO.CommitUsers commitUsers = new CommitsUserDTO.CommitUsers();
                                commitUsers.setAvatar_url(commitsDTO.getCommitter().getAvatar_url());
                                commitUsers.setCount(1);
                                commitUsers.setEvents_url(commitsDTO.getCommitter().getEvents_url());
                                commitUsers.setFollowers_url(commitsDTO.getCommitter().getFollowers_url());
                                commitUsers.setFollowing_url(commitsDTO.getCommitter().getFollowing_url());
                                commitUsers.setGists_url(commitsDTO.getCommitter().getGists_url());
                                commitUsers.setGravatar_id(commitsDTO.getCommitter().getGravatar_id());
                                commitUsers.setHtml_url(commitsDTO.getCommitter().getHtml_url());
                                commitUsers.setId(commitsDTO.getCommitter().getId());
                                commitUsers.setImpact("Low");
                                commitUsers.setLogin(commitsDTO.getCommitter().getLogin());
                                commitUsers.setNode_id(commitsDTO.getCommitter().getNode_id());
                                commitUsers.setOrganizations_url(commitsDTO.getCommitter().getOrganizations_url());
                                commitUsers.setReceived_events_url(commitsDTO.getCommitter().getReceived_events_url());
                                commitUsers.setRepos_url(commitsDTO.getCommitter().getRepos_url());
                                commitUsers.setSite_admin(commitsDTO.getCommitter().isSite_admin());
                                commitUsers.setStarred_url(commitsDTO.getCommitter().getStarred_url());
                                commitUsers.setSubscriptions_url(commitsDTO.getCommitter().getSubscriptions_url());
                                commitUsers.setType(commitsDTO.getCommitter().getType());
                                commitUsers.setUrl(commitsDTO.getCommitter().getUrl());
                                String date = commitsDTO.getCommit().getCommitter().getDate();
                                commitUsers.setOrderid(check);

                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

                                LocalDate date1 = LocalDate.parse(date);
                                System.out.println(formatter.format(date1));
                                Date datedate = new Date(formatter.format(date1));

                                commitUsers.setDate(datedate);
                                commitUsersList.add(commitUsers);

                            }
                        }else {

                            CommitsUserDTO.CommitUsers commitUsers = new CommitsUserDTO.CommitUsers();
                            commitUsers.setAvatar_url(commitsDTO.getCommitter().getAvatar_url());
                            commitUsers.setCount(1);
                            commitUsers.setEvents_url(commitsDTO.getCommitter().getEvents_url());
                            commitUsers.setFollowers_url(commitsDTO.getCommitter().getFollowers_url());
                            commitUsers.setFollowing_url(commitsDTO.getCommitter().getFollowing_url());
                            commitUsers.setGists_url(commitsDTO.getCommitter().getGists_url());
                            commitUsers.setGravatar_id(commitsDTO.getCommitter().getGravatar_id());
                            commitUsers.setHtml_url(commitsDTO.getCommitter().getHtml_url());
                            commitUsers.setId(commitsDTO.getCommitter().getId());
                            commitUsers.setImpact("Low");
                            commitUsers.setLogin(commitsDTO.getCommitter().getLogin());
                            commitUsers.setNode_id(commitsDTO.getCommitter().getNode_id());
                            commitUsers.setOrganizations_url(commitsDTO.getCommitter().getOrganizations_url());
                            commitUsers.setReceived_events_url(commitsDTO.getCommitter().getReceived_events_url());
                            commitUsers.setRepos_url(commitsDTO.getCommitter().getRepos_url());
                            commitUsers.setSite_admin(commitsDTO.getCommitter().isSite_admin());
                            commitUsers.setStarred_url(commitsDTO.getCommitter().getStarred_url());
                            commitUsers.setSubscriptions_url(commitsDTO.getCommitter().getSubscriptions_url());
                            commitUsers.setType(commitsDTO.getCommitter().getType());
                            commitUsers.setUrl(commitsDTO.getCommitter().getUrl());
                            String date = commitsDTO.getCommit().getCommitter().getDate();

                            commitUsers.setOrderid(check);

                    //        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);


                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                       //     SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                          //  SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");


                            Date date1 = formatter.parse(date);

                         //   LocalDate date1 = LocalDate.parse(date, inputFormatter);

                          //  System.out.println(formatter.format(date1));
                        //    Date datedate = new Date(inputFormatter.format(date1));

                            commitUsers.setDate(date1);

                            commitUsersList.add(commitUsers);

                            nextquickly = 0;
                            System.out.println("end nextquickly");
                        }
                    }


                    int lenusercommit = commitsDTOList.size();
                    int check2 = commitsDTOList.size();
                    check2= check2-1;
                    System.out.println("nextquickly");
                    System.out.println(check2);
                    System.out.println(lenusercommit);
                    for(int len = 0; len < check2; len++) {


                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

                        String dateone = commitsDTOList.get(len).getCommit().getCommitter().getDate();
                        Date datedate1 = formatter.parse(dateone);
                        System.out.println("nextquickly 2");
                       // LocalDate date1 = LocalDate.parse();
                      //  System.out.println(formatter.format(date1));

                     //   Date datedate1 = new Date(formatter.format(date1));

                        for (int i = 0; i < lenusercommit; i++) {
                            System.out.println("nextquickly 3");
                            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            String date2 = commitsDTOList.get(check2).getCommit().getCommitter().getDate();

                            Date datedate2 = formatter2.parse(date2);
                            System.out.println("nextquickly 4");
                            System.out.println("nextquickly 5");
                            long difference_In_Time = datedate1.getTime() - datedate2.getTime();
                            long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
                            if (difference_In_Days < 7) {
                                weekone = weekone + 1;
                            } else if (difference_In_Days < 15 && difference_In_Days > 7) {
                                weektwo = weektwo + 1;

                            } else if (difference_In_Days < 21 && difference_In_Days > 14) {
                                weekthree = weekthree + 1;
                            }else {
                                weekfour = weekfour + 1;
                            }
                            check2 = check2 -1;

                        }

                    }
                    System.out.println("end commituserlist");

                }
                commitsUserDTO.setCommitUsersList(commitUsersList);
                String feedback = "";
                if(weekfour > weekthree && weektwo < weekthree){
                    feedback = "The commits would most likely increase";
                }else  if(weekfour > weekthree && weektwo > weekthree & weektwo > weekone){
                    feedback = "The commits would might likely increase";
                }else  if(weekfour > weekthree && weektwo > weekthree & weektwo < weekone){
                    feedback = "The commits would might not likely increase";
                }else  if(weekfour < weekthree && weektwo > weekthree & weektwo < weekone){
                    feedback = "The commits would most likely decrease";
                }else  if(weekfour > weekthree && weektwo < weekthree & weektwo > weekone){
                    feedback = "The commits would increase";
                }else{
                    feedback = "much data is still required to forecast";
                }

                commitsUserDTO.setProjection(feedback);
                projectionbyCodeSearchDTO.setCommitsUserDTO(commitsUserDTO);

            }

            return new ResponseEntity<>(projectionbyCodeSearchDTO, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(projectionbyCodeSearchDTO, HttpStatus.GATEWAY_TIMEOUT);
        }

    }


}

