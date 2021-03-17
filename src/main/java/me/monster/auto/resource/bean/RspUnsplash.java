package me.monster.auto.resource.bean;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/15 3:02 下午
 */
public class RspUnsplash {
    private String id;
    private String created_at;
    private String updated_at;
    private String promoted_at;
    private int width;
    private int height;
    private String color;
    private String blur_hash;
    private String description;
    private String alt_description;
    private UrlsBean urls;
    private LinksBean links;
    private int likes;
    private boolean liked_by_user;
    private UserBean user;
    private ExifBean exif;
    private int views;
    private int downloads;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getPromoted_at() {
        return promoted_at;
    }

    public void setPromoted_at(String promoted_at) {
        this.promoted_at = promoted_at;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBlur_hash() {
        return blur_hash;
    }

    public void setBlur_hash(String blur_hash) {
        this.blur_hash = blur_hash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlt_description() {
        return alt_description;
    }

    public void setAlt_description(String alt_description) {
        this.alt_description = alt_description;
    }

    public UrlsBean getUrls() {
        return urls;
    }

    public void setUrls(UrlsBean urls) {
        this.urls = urls;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLiked_by_user() {
        return liked_by_user;
    }

    public void setLiked_by_user(boolean liked_by_user) {
        this.liked_by_user = liked_by_user;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public ExifBean getExif() {
        return exif == null ? new ExifBean() : exif;
    }

    public void setExif(ExifBean exif) {
        this.exif = exif;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public static class UrlsBean {
        private String raw;
        private String full;
        private String regular;
        private String small;
        private String thumb;

        public String getRaw() {
            return raw == null ? "" : raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public String getFull() {
            return full == null ? "" : full;
        }

        public void setFull(String full) {
            this.full = full;
        }

        public String getRegular() {
            return regular == null ? "" : regular;
        }

        public void setRegular(String regular) {
            this.regular = regular;
        }

        public String getSmall() {
            return small == null ? "" : small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getThumb() {
            return thumb == null ? "" : thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }

    public static class LinksBean {
        private String self;
        private String html;
        private String download;
        private String download_location;

        public String getSelf() {
            return self == null ? "" : self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getHtml() {
            return html == null ? "" : html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public String getDownload() {
            return download == null ? "" : download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public String getDownload_location() {
            return download_location == null ? "" : download_location;
        }

        public void setDownload_location(String download_location) {
            this.download_location = download_location;
        }
    }

    public static class UserBean {
        private String id;
        private String updated_at;
        private String username;
        private String name;
        private String first_name;
        private String last_name;
        private String twitter_username;
        private String portfolio_url;
        private String bio;
        private String location;
        private LinksBean links;
        private ProfileImageBean profile_image;
        private String instagram_username;
        private int total_collections;
        private int total_likes;
        private int total_photos;
        private boolean accepted_tos;
        private boolean for_hire;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUpdated_at() {
            return updated_at == null ? "" : updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getUsername() {
            return username == null ? "" : username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirst_name() {
            return first_name == null ? "" : first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name == null ? "" : last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getTwitter_username() {
            return twitter_username == null ? "" : twitter_username;
        }

        public void setTwitter_username(String twitter_username) {
            this.twitter_username = twitter_username;
        }

        public String getPortfolio_url() {
            return portfolio_url == null ? "" : portfolio_url;
        }

        public void setPortfolio_url(String portfolio_url) {
            this.portfolio_url = portfolio_url;
        }

        public String getBio() {
            return bio == null ? "" : bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getLocation() {
            return location == null ? "" : location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public LinksBean getLinks() {
            return links == null ? new LinksBean() : links;
        }

        public void setLinks(LinksBean links) {
            this.links = links;
        }

        public ProfileImageBean getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(ProfileImageBean profile_image) {
            this.profile_image = profile_image;
        }

        public String getInstagram_username() {
            return instagram_username == null ? "" : instagram_username;
        }

        public void setInstagram_username(String instagram_username) {
            this.instagram_username = instagram_username;
        }

        public int getTotal_collections() {
            return total_collections;
        }

        public void setTotal_collections(int total_collections) {
            this.total_collections = total_collections;
        }

        public int getTotal_likes() {
            return total_likes;
        }

        public void setTotal_likes(int total_likes) {
            this.total_likes = total_likes;
        }

        public int getTotal_photos() {
            return total_photos;
        }

        public void setTotal_photos(int total_photos) {
            this.total_photos = total_photos;
        }

        public boolean isAccepted_tos() {
            return accepted_tos;
        }

        public void setAccepted_tos(boolean accepted_tos) {
            this.accepted_tos = accepted_tos;
        }

        public boolean isFor_hire() {
            return for_hire;
        }

        public void setFor_hire(boolean for_hire) {
            this.for_hire = for_hire;
        }

        public static class LinksBean {
            private String self;
            private String html;
            private String photos;
            private String likes;
            private String portfolio;
            private String following;
            private String followers;

            public String getSelf() {
                return self == null ? "" : self;
            }

            public void setSelf(String self) {
                this.self = self;
            }

            public String getHtml() {
                return html == null ? "" : html;
            }

            public void setHtml(String html) {
                this.html = html;
            }

            public String getPhotos() {
                return photos == null ? "" : photos;
            }

            public void setPhotos(String photos) {
                this.photos = photos;
            }

            public String getLikes() {
                return likes == null ? "" : likes;
            }

            public void setLikes(String likes) {
                this.likes = likes;
            }

            public String getPortfolio() {
                return portfolio == null ? "" : portfolio;
            }

            public void setPortfolio(String portfolio) {
                this.portfolio = portfolio;
            }

            public String getFollowing() {
                return following == null ? "" : following;
            }

            public void setFollowing(String following) {
                this.following = following;
            }

            public String getFollowers() {
                return followers == null ? "" : followers;
            }

            public void setFollowers(String followers) {
                this.followers = followers;
            }
        }

        public static class ProfileImageBean {
            private String small;
            private String medium;
            private String large;

            public String getSmall() {
                return small == null ? "" : small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getMedium() {
                return medium == null ? "" : medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getLarge() {
                return large == null ? "" : large;
            }

            public void setLarge(String large) {
                this.large = large;
            }
        }
    }

    public static class ExifBean {
        private String make;
        private String model;
        private String exposure_time;
        private String aperture;
        private String focal_length;
        private int iso;

        public String getMake() {
            return make == null ? "" : make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getModel() {
            return model == null ? "" : model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getExposure_time() {
            return exposure_time == null ? "" : exposure_time;
        }

        public void setExposure_time(String exposure_time) {
            this.exposure_time = exposure_time;
        }

        public String getAperture() {
            return aperture == null ? "" : aperture;
        }

        public void setAperture(String aperture) {
            this.aperture = aperture;
        }

        public String getFocal_length() {
            return focal_length == null ? "" : focal_length;
        }

        public void setFocal_length(String focal_length) {
            this.focal_length = focal_length;
        }

        public int getIso() {
            return iso;
        }

        public void setIso(int iso) {
            this.iso = iso;
        }
    }
}
