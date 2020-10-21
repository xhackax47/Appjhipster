export interface IClientCyber06 {
  id?: number;
  firstname?: string;
  name?: string;
}

export class ClientCyber06 implements IClientCyber06 {
  constructor(public id?: number, public firstname?: string, public name?: string) {}
}
