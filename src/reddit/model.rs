use chrono;
use std::option::Option;
use std::vec::Vec;
pub trait Thing {
    fn id(&self) -> &String;
    fn name(&self) -> &String;
    fn kind(&self) -> &String;
}

#[derive(Getters)]
pub struct Listing {
    pub before: Option<String>,
    pub after: Option<String>,
    pub modhash: String,
    pub children: Vec<Box<Thing>>,
}

trait Votable {
    fn ups(&self) -> i64;
    fn downs(&self) -> i64;
    fn likes(&self) -> &Option<bool>;
}

trait Created {
    fn created_utc(&self) -> chrono::NaiveDateTime;
}

#[derive(Getters)]
struct Comment {
    ups: i64,
    downs: i64,
    likes: Option<bool>,
    id: String,
    name: String,
    kind: String,
    #[get]
    approved_by: Option<String>,
    #[get]
    author: String,
    #[get]
    author_flair_css_class: Option<String>,
    #[get]
    author_flair_text: Option<String>,
    #[get]
    banned_by: Option<String>,
    #[get]
    body: String,
    #[get]
    body_html: String,
    #[get]
    edited: Edit,
    #[get]
    gilded: i32,
    #[get]
    link_author: Option<String>,
    #[get]
    link_id: String,
    #[get]
    link_title: Option<String>,
    #[get]
    link_url: Option<String>,
    #[get]
    num_repots: Option<i64>,
    #[get]
    parent_id: String,
    #[get]
    replies: Vec<Comment>,
    #[get]
    saved: Option<bool>,
    #[get]
    score: i64,
    #[get]
    score_hidden: bool,
    #[get]
    subreddit: String,
    #[get]
    subreddit_id: String,
    #[get]
    distinguished: Option<String>,
}

enum Edit {
    No,
    Yes(chrono::NaiveDateTime),
    YesOld,
}

impl Thing for Comment {
    fn id(&self) -> &String {
        &self.id
    }

    fn name(&self) -> &String {
        &self.name
    }

    fn kind(&self) -> &String {
        &self.kind
    }
}

impl Votable for Comment {
    fn ups(&self) -> i64 {
        self.ups
    }

    fn downs(&self) -> i64 {
        self.downs
    }

    fn likes(&self) -> &Option<bool> {
        &self.likes
    }
}
