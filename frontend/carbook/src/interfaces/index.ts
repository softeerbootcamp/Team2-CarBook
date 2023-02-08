type Category = 'type' | 'model' | 'hashtag';

export interface IHashTag {
  id: string;
  category: Category;
  tag: string;
}
