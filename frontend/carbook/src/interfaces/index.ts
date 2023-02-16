export type CategoryType = 'type' | 'model' | 'hashtag' | 'all';

export interface IHashTag {
  id: string;
  category: CategoryType;
  tag: string;
}

export interface INewHashTag {
  [tag: string]: 'new' | 'old';
}

export interface IImage {
  postId: number;
  imageUrl: string;
}

export interface IFollows {
  nickname: string;
}

export type IPostIndex = 'type' | 'model' | 'imageUrl' | 'hashtags' | 'content';

export interface IPost {
  type: string;
  model: string;
  imageUrl: object;
  hashtags: string[];
  content: string;
}
