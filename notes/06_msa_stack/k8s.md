## K8S

#### 역할 (로그로 파악 필요)
- github
    - manifests
    - application
- bamboo
    - manifests
    - application
- k8s
    - clusters
    - services
    - pods
    - deployments
- spinnaker
    - Bake
    - Deploy
- kops

#### kubectl 설치
```bash
$ brew install kubectl
$ kubectl version --short
# Client Version: v1.13.3
# Server Version: v1.10.11
```

#### kubectl 설정
```bash
# k8s 클러스터별 접속 정보
$ vim ~/.kube/config
```

#### k8s 콘솔 접속

> kubectl cheatsheet https://kubernetes.io/ko/docs/reference/kubectl/cheatsheet/

```bash
# k8s 터널링
# http://localhost:8002/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/#!/overview?namespace=default
# alias: https://meshkorea.slack.com/archives/D76KZB79C/p1550541807000300
$ kubectl --context=k8s-qa.meshdev.io --port=8002 proxy
```

```bash
$ kubectl config
# Modify kubeconfig files using subcommands like "kubectl config set current-context my-context"

$ kubectl config get-contexts
# CURRENT   NAME                    CLUSTER                 AUTHINFO                NAMESPACE
# *         k8s-dev.abcdefg.io      k8s-dev.abcdefg.io      k8s-dev.abcdefg.io

$ kubectl proxy
# Starting to serve on 127.0.0.1:8001
```

#### AWS 리소스 접속
```bash
# AWS pem
# bastion: bastion.k8s-qa.meshdev.io
$ chmod 400 ~/.ssh/meshdev_k8s_qa.pem
```
